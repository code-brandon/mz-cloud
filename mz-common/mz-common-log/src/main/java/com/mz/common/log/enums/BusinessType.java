package com.mz.common.log.enums;

/**
 * What -- 业务类型
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.log.enums
 * @ClassName: BusinessType
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/13 1:59
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    SAVE,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    REMOVE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空数据
     */
    CLEAN,
}
