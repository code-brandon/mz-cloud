package com.mz.common.mybatis.exception;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mz.common.constant.Constant;
import com.mz.common.constant.enums.MzErrorCodeEnum;
import com.mz.common.utils.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Optional;


/**
 * What -- Mybatis异常处理器
 * <br>
 * Describe -- 如果时出现404的话，是不被GlobalExceptionHandler处理的
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzGlobalExceptionHandler
 * @CreateTime 2022/5/20 21:54
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = {"org.springframework.jdbc", "java.sql", Constant.PACKAGE_PRE_FIX})
public class MzMybatisExceptionHandler {

    /**
     * 结果过多异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TooManyResultsException.class)
    public JSONObject tooManyResultsException(@NotNull TooManyResultsException e) {
        String message = e.getMessage();
        log.error("结果过多异常:{}", e.getMessage());
        return JSONUtil.parseObj(String.format("{code:%d,message:'%s',timestamp:%d}", MzErrorCodeEnum.SQL_TOO_MANY_RESULTS_EXCEPTION.getCode(), message, System.currentTimeMillis()));
    }


    /**
     * 参数绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    public JSONObject bindingException(@NotNull BindingException e) {
        String message = e.getMessage();
        log.error("参数绑定异常:{}", e.getMessage());
        return JSONUtil.parseObj(String.format("{code:%d,message:'%s',timestamp:%d}", MzErrorCodeEnum.SQL_BINDING_EXCEPTION.getCode(), message.replace("'", "\\'").replace("\n", ""), System.currentTimeMillis()));
    }

    /**
     * SQL语句异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public JSONObject sqlException(@NotNull SQLException e) {
        String message = e.getMessage();
        log.error("SQL语句异常:{}", e.getMessage());
        return JSONUtil.parseObj(String.format("{code:%d,message:'%s',timestamp:%d}", MzErrorCodeEnum.SQL_EXCEPTION.getCode(), getReplace(message), System.currentTimeMillis()));
    }

    /**
     * SQL语句异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public JSONObject badSqlGrammarException(BadSqlGrammarException e) {
        String stackTraceByPn = ThrowableUtils.getStackTraceByPn(e, Constant.PACKAGE_PRE_FIX);
        log.error("糟糕的SQL语法异常:{}", stackTraceByPn);
        String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
        return JSONUtil.parseObj(String.format("{code:%d,message:'%s',timestamp:%d}", MzErrorCodeEnum.SQL_GRAMMAR_EXCEPTION.getCode(), message.replace("'", "\\'").replace("\n", ""), System.currentTimeMillis()));
    }

    @NotNull
    private static String getReplace(@NotNull String message) {
        return message.replace("'", "\\'").replace("\n", "");
    }
}