package com.mz.common.core.exception;

import com.mz.common.core.utils.MessageUtils;
import org.springframework.util.StringUtils;

/**
 * What -- 基础异常
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzBaseException
 * @CreateTime 2022/5/20 21:54
 */
public class MzBaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public MzBaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public MzBaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public MzBaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public MzBaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public MzBaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
