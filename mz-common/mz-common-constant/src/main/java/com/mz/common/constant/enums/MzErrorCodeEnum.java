package com.mz.common.constant.enums;

/**
 * What -- 错误码和错误信息定义类
 * <br>
 * Describe --
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: MzCodeEnum
 * @CreateTime 2022/5/20 21:51
 */
public enum MzErrorCodeEnum implements KeyValueEnum<Integer,String>{

    /**
     * 参数错误
     */
    PARAM_ERROR(405, "参数错误"),
    /**
     * 系统未知异常
     */
    UNKNOW_EXCEPTION(10000,"系统未知异常",""),
    /**
     * 参数格式校验失败
     */
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    SMS_CODE_EXCEPTION(10002,"验证码获取频率太高，请稍后再试"),
    OAUTH_EXCEPTION(15000,"OAuth异常!"),
    USER_EXIST_EXCEPTION(15001,"用户存在!"),
    PHONE_EXIST_EXCEPTION(15002,"手机号存在!"),
    LOGINACCT_PASSWORD_INVAILD_EXCEPTION(15003,"账号或密码错误!"),
    OAUTH_AUTH_EXCEPTION(15004,"身份验证失败!"),
    OAUTH_ACCESS_EXCEPTION(15006,"访问异常!"),
    OAUTH_TOKEN_EXCEPTION(15007,"Token无效或过期!"),
    OAUTH_GRANTTYPE_EXCEPTION(15008,"不支持的授予类型!"),
    OAUTH_CLIENT_EXCEPTION(15009,"远程客户端出现异常!" ),

    FEIGN_EXCEPTION(20000,"Feign远程调用异常!"),
    REMOTE_EXCEPTION(20001,"远程调用异常!"),

    /**
     * SQL异常
     */
    SQL_TOO_MANY_RESULTS_EXCEPTION(12001,"结果过多异常!"),
    SQL_BINDING_EXCEPTION(12002,"参数绑定异常!"),
    SQL_EXCEPTION(12003,"SQL语句异常!"),
    SQL_GRAMMAR_EXCEPTION(12004,"SQL语法错误!"),

    ;

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 信息
     */
    private final String msg;

    private String i18Key;

    MzErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    MzErrorCodeEnum(int code, String msg,String i18Key) {
        this.code = code;
        this.msg = msg;
        this.i18Key = i18Key;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getI18Key() {
        return i18Key;
    }

    /**
     * 获取枚举键
     *
     * @return
     */
    @Override
    public Integer getKey() {
        return getCode();
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    @Override
    public String getValue() {
        return getMsg();
    }
}
