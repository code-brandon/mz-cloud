package com.mz.common.mybatis.plugin;

import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.mz.common.utils.SqlFormatterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mz.common.utils.MzUtils.getFormatLogString;

/**
 * What -- 美化SQL语句
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzParameterInterceptor
 * @CreateTime 2022/12/5 14:04
 */
@Intercepts(@Signature(
        type = ParameterHandler.class,
        method = "setParameters",
        args = {PreparedStatement.class}
))
@Slf4j
public class MzParameterInterceptor implements Interceptor {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object target =  invocation.getTarget();
        Class<?> aClass = (target instanceof MybatisParameterHandler ? (MybatisParameterHandler)target :  (DefaultParameterHandler)target).getClass();
        Field boundSqlField = aClass.getDeclaredField("boundSql");
        Field configurationField = aClass.getDeclaredField("configuration");
        Field mappedStatementField = aClass.getDeclaredField("mappedStatement");
        boundSqlField.setAccessible(true);
        configurationField.setAccessible(true);
        mappedStatementField.setAccessible(true);
        BoundSql boundSql = (BoundSql) boundSqlField.get(target);
        Configuration configuration = (Configuration) configurationField.get(target);
        MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(target);
        String showSql = showSql(configuration, boundSql);

        Log thisLog = mappedStatement.getStatementLog();
        thisLog.debug(getFormatLogString(SqlFormatterUtils.getPrettySql(showSql), 33, 0));
        //直接返回结果
        return invocation.proceed();
    }

    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            value = "'" + SIMPLE_DATE_FORMAT.format(obj) + "'";
        } else if (obj != null) {
            value = obj.toString();
        } else {
            value = "";
        }

        return value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        try {
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
            if (!parameterMappings.isEmpty() && parameterObject != null) {
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);

                    for (ParameterMapping parameterMapping : parameterMappings) {
                        String propertyName = parameterMapping.getProperty();
                        Object obj;
                        if (metaObject.hasGetter(propertyName)) {
                            obj = metaObject.getValue(propertyName);
                            sql = sql.replaceFirst("\\?", getParameterValue(obj));
                        } else if (boundSql.hasAdditionalParameter(propertyName)) {
                            obj = boundSql.getAdditionalParameter(propertyName);
                            sql = sql.replaceFirst("\\?", getParameterValue(obj));
                        }
                    }
                }
            }

            return sql;
        } catch (Exception var11) {
            return "";
        }
    }
}