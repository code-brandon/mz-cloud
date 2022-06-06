package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictTypeEntity;

import java.util.Map;

/**
 * 字典类型表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysDictTypeService extends IService<SysDictTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

