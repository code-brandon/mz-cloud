package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.dto.SysOperLogDto;
import com.mz.system.model.entity.SysOperLogEntity;

import java.util.Map;

/**
 * 操作日志记录
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysOperLogService extends IService<SysOperLogEntity> {

    /**
     * 分页查询操作日志
     *
     * @param params 分页参数
     * @return 分页数据
     */
    PageUtils<SysOperLogEntity> queryPage(Map<String, Object> params);

    /**
     * 保存操作日志
     *
     * @param sysOperLog 实体对象
     * @return true：成功，false：失败
     */
    boolean  saveOperLog(SysOperLogDto sysOperLog);
}

