package com.mz.common.constant.enums;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.constant.enums
 * @ClassName: MzDataAuthEnum
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/25 22:49
 */
public enum MzDataAuthEnum implements KeyValueEnum<Integer,String>{

    /**
     * 全部数据权限
     */
    DATA_SCOPE_ALL(1,"全部数据权限"),

    /**
     * 自定数据权限
     */
    DATA_SCOPE_CUSTOM(2,"自定数据权限"),

    /**
     * 部门数据权限
     */
    DATA_SCOPE_DEPT (3,"部门数据权限"),

    /**
     * 部门及以下数据权限
     */
    DATA_SCOPE_DEPT_AND_CHILD(4,"部门及以下数据权限"),

    /**
     * 仅本人数据权限
     */
    DATA_SCOPE_SELF(5,"仅本人数据权限");

    /**
     * 状态码
     */
    private final Integer scope;
    /**
     * 信息
     */
    private final String desc;

    MzDataAuthEnum(Integer scope, String desc) {
        this.scope = scope;
        this.desc = desc;
    }

    /**
     * 获取枚举键
     *
     * @return
     */
    @Override
    public Integer getKey() {
        return scope;
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    @Override
    public String getValue() {
        return desc;
    }
}
