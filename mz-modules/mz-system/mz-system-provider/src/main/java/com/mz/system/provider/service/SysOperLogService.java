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

    PageUtils<SysOperLogEntity> queryPage(Map<String, Object> params);

    boolean  saveOperLog(SysOperLogDto sysOperLog);
}

