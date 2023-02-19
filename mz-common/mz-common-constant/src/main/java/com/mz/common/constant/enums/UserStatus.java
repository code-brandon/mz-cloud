package com.mz.common.constant.enums;

/**
 * What -- 用户状态
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: UserStatus
 * @CreateTime 2022/5/20 21:55
 */
public enum UserStatus implements KeyValueEnum<String,String>{
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    /**
     * 获取枚举键
     *
     * @return
     */
    @Override
    public String getKey() {
        return getCode();
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    @Override
    public String getValue() {
        return getInfo();
    }
}