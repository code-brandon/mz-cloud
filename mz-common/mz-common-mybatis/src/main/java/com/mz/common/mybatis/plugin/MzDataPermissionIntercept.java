package com.mz.common.mybatis.plugin;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mz.common.constant.enums.MzDataAuthEnum;
import com.mz.common.mybatis.annotation.MzDataAuth;
import com.mz.common.mybatis.cache.MzMapperClass;
import com.mz.common.mybatis.cache.MzMapperMethod;
import com.mz.common.mybatis.cache.MzMybatisCache;
import com.mz.common.utils.MzUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * What -- Mybatis 数据权限拦截器插件
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzDataPermissionIntercept
 * @CreateTime 2023/2/27 15:27
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Slf4j
public class MzDataPermissionIntercept implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            Executor executor = (Executor) invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            //由于逻辑关系，只会进入一次
            if (args.length == 4) {
                //4 个参数时
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                //6 个参数时
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }
            //TODO 自己要进行的各种处理
            String sql = boundSql.getSql();
            String className = ms.getId().substring(0, ms.getId().lastIndexOf('.'));
            String methodName = ms.getId().substring(ms.getId().lastIndexOf('.') + 1);
            MzMapperClass mzMapperValue = MzMybatisCache.getMzMapperValue(className);
            Class<MzDataAuth> mzDataAuthClass = MzDataAuth.class;

            MzDataAuth dataAuthClass = AnnotationUtil.getAnnotation(mzMapperValue.getAClass(), mzDataAuthClass);
            boolean isDataAuthClass = MzUtils.notEmpty(dataAuthClass);

            MzMapperMethod mzMapperMethod = mzMapperValue.getMethod(methodName, parameter);
            MzDataAuth dataAuthMethod = AnnotationUtil.getAnnotation(mzMapperMethod.getMethod(), mzDataAuthClass);
            boolean isDataAuthMethod = MzUtils.notEmpty(dataAuthMethod);
            log.info("mzMapperMethod = " + mzMapperMethod);

            if (!MzMybatisCache.getMzIgnoreDataAuth()) {
                if (isDataAuthClass || isDataAuthMethod) {
                    if (isDataAuthMethod && !dataAuthMethod.ignore()) {
                        return getExecutorQuery(ms, parameter, rowBounds, resultHandler, executor, cacheKey, boundSql, sql, dataAuthClass, isDataAuthClass, dataAuthMethod, isDataAuthMethod);
                    } else if (!isDataAuthMethod && isDataAuthClass) {
                        return getExecutorQuery(ms, parameter, rowBounds, resultHandler, executor, cacheKey, boundSql, sql, dataAuthClass, isDataAuthClass, dataAuthMethod, isDataAuthMethod);
                    }

                }
            }
            //注：下面的方法可以根据自己的逻辑调用多次，在分页插件中，count 和 page 各调用了一次
            return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        } finally {

        }
    }

    private Object getExecutorQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, Executor executor, CacheKey cacheKey, BoundSql boundSql, String sql, MzDataAuth dataAuthClass, boolean isDataAuthClass, MzDataAuth dataAuthMethod, boolean isDataAuthMethod) throws JSQLParserException, SQLException {
        // 增强sql
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            this.setWhere((PlainSelect) selectBody, isDataAuthClass, isDataAuthMethod, dataAuthClass, dataAuthMethod);
        } else if (selectBody instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach((s) -> {
                this.setWhere((PlainSelect) s, isDataAuthClass, isDataAuthMethod, dataAuthClass, dataAuthMethod);
            });
        }
        String dataPermissionSql = select.toString();
        log.debug("增强SQL： {}", dataPermissionSql);
        BoundSql dataPermissionBoundSql = new BoundSql(ms.getConfiguration(), dataPermissionSql, boundSql.getParameterMappings(), parameter);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, dataPermissionBoundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 设置SQL条件
     *
     * @param plainSelect
     * @param isDataAuthClass
     * @param isDataAuthMethod
     * @param dataAuthClass
     * @param dataAuthMethod
     */
    protected void setWhere(PlainSelect plainSelect, boolean isDataAuthClass, boolean isDataAuthMethod, MzDataAuth dataAuthClass, MzDataAuth dataAuthMethod) {
        Expression sqlSegment = this.getSqlSegment(plainSelect, isDataAuthClass, isDataAuthMethod, dataAuthClass, dataAuthMethod);
        if (null != sqlSegment) {
            plainSelect.setWhere(sqlSegment);
        }
    }

    /**
     * 获取权限SQL
     *
     * @param plainSelect
     * @param isDataAuthClass
     * @param isDataAuthMethod
     * @param dataAuthClass
     * @param dataAuthMethod
     * @return
     */
    @SneakyThrows
    public Expression getSqlSegment(PlainSelect plainSelect, boolean isDataAuthClass, boolean isDataAuthMethod, MzDataAuth dataAuthClass, MzDataAuth dataAuthMethod) {
        Expression where = plainSelect.getWhere();
        JSONObject loginUser = getUserInfo();
        if (MzUtils.isEmpty(loginUser)) {
            return where;
        }
        MzDataAuth dataAuth = null;

        if (isDataAuthClass) {
            dataAuth = dataAuthClass;
        }
        if (isDataAuthMethod) {
            dataAuth = dataAuthMethod;
        }
        if (MzUtils.isEmpty(dataAuth)) {
            throw new SQLException("为找到 MzDataAuth");
        }
        String deptAlias = dataAuth.deptAlias();
        String userAlias = dataAuth.userAlias();
        String userField = dataAuth.userField();
        String deptField = dataAuth.deptField();
        String userFrom = dataAuth.userFrom();
        String deptFrom = dataAuth.deptFrom();

        String newUserAlias = setNewAlias(plainSelect, userAlias, userFrom);
        String newDeptAlias = setNewAlias(plainSelect, deptAlias, deptFrom);


        Long deptId = loginUser.getLong("deptId");
        Long userId = loginUser.getLong("userId");
        String dataScopeStr = loginUser.getStr("dataScopes");
        String roleIds = loginUser.getStr("roleIds");
        Set<String> dataScopes = StringUtils.commaDelimitedListToSet(dataScopeStr);
        StringBuilder sqlString = new StringBuilder();
        for (String dataScope : dataScopes) {
            if (dataScope.equalsIgnoreCase(MzDataAuthEnum.DATA_SCOPE_ALL.getValue())) {
                // 全部数据权限
                sqlString = new StringBuilder();
            } else if (dataScope.equalsIgnoreCase(MzDataAuthEnum.DATA_SCOPE_CUSTOM.getValue())) {
                String format = String.format(" OR %1$s.%2$s IN ( SELECT %2$s FROM `sys_role_dept` WHERE FIND_IN_SET(role_id,'%3$s'))", userAlias, deptField, roleIds);
                sqlString.append(format);
            } else if (dataScope.equalsIgnoreCase(MzDataAuthEnum.DATA_SCOPE_DEPT.getValue())) {
                String format = "";
                if (MzUtils.notEmpty(deptAlias)) {
                    format = String.format(" OR %1$s.%2$s = %3$s", deptAlias, deptField, deptId);
                }else {
                    format = String.format(" OR %1$s.%2$s = %3$s", userAlias, deptField, deptId);
                }
                sqlString.append(format);
            } else if (dataScope.equalsIgnoreCase(MzDataAuthEnum.DATA_SCOPE_DEPT_AND_CHILD.getValue()) && MzUtils.notEmpty(deptAlias)) {
                String format = String.format(" OR FIND_IN_SET(%1$s.%2$s,%1$s.ancestors) ", deptAlias, deptField);
                sqlString.append(format);
            } else if (dataScope.equalsIgnoreCase(MzDataAuthEnum.DATA_SCOPE_SELF.getValue())) {
                if (MzUtils.notEmpty(userAlias)) {
                    sqlString.append(String.format(" OR %1$s.%2$s = %3$s ", userAlias, userField, userId));
                } else {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(String.format(" OR %1$s.%2$s = 0 ", deptAlias, deptField));
                }
            }
        }

        if (MzUtils.notEmpty(sqlString.toString())) {
            if (MzUtils.isEmpty(where)) {
               where = CCJSqlParserUtil.parseCondExpression(sqlString.toString());
            }else {
                sqlString.insert(0, " AND (");
                sqlString.append(")");
                sqlString.delete(7, 9);
                where = CCJSqlParserUtil.parseCondExpression(where + sqlString.toString());
            }
        }
        return where;
    }

    /**
     * 检索别名 并设置，没有返回原值
     *
     * @param plainSelect
     * @param newAlias    改变赋值的别名
     * @param alias
     * @param from
     */
    private String setNewAlias(PlainSelect plainSelect, String alias, String from) {
        String newAlias = "";
        FromItem fromItem = plainSelect.getFromItem();
        Table table = (Table) fromItem;
        String tableName = table.getName();
        String nameAlias = table.getAlias().getName();
        List<Join> joins = plainSelect.getJoins();

        // 去掉 ` 后相同  用 jsqlparser 解析的 原因，后面分页插件 对LEFT JINO 优化根据别名 所以别名 要和 真正SQL的别名保持一直
        if (from.replaceAll("`", "").equalsIgnoreCase(tableName.replaceAll("`", ""))) {
            if (MzUtils.notEmpty(nameAlias)) {
                if (alias.replaceAll("`", "").equalsIgnoreCase(nameAlias.replaceAll("`", ""))) {
                    newAlias = nameAlias;
                }
            }else {
                newAlias = tableName;
            }
        } else {
            if (MzUtils.notEmpty(joins)) {
                for (Join join : joins) {
                    FromItem rightItem = join.getRightItem();
                    Table rightable = (Table) rightItem;
                    String righTableName = rightable.getName();
                    String righNameAlias = rightable.getAlias().getName();
                    if (from.replaceAll("`", "").equalsIgnoreCase(righTableName.replaceAll("`", ""))) {
                        if (MzUtils.notEmpty(righNameAlias)) {
                            if (alias.replaceAll("`", "").equalsIgnoreCase(righNameAlias.replaceAll("`", ""))) {
                                newAlias = righNameAlias;
                            }
                        }else {
                            newAlias = tableName;
                        }
                    }
                }
            }
        }
        return newAlias;
    }

    /**
     * 获取当前 SecurityContex 中的用户信息
     *
     * @return 用户信息JSON
     */
    private JSONObject getUserInfo() {
        //判断线程内是否有权限信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return JSONUtil.parseObj(principal);
    }

}