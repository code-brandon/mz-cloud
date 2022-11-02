package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysNoticeEntity;

import java.util.Map;

/**
 * 通知公告表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysNoticeService extends IService<SysNoticeEntity> {

    PageUtils<SysNoticeEntity> queryPage(Map<String, Object> params);
}

