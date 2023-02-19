package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictTypeEntity;
import com.mz.system.model.vo.req.SysDictTypeReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;

import java.util.Map;

/**
 * 字典类型表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysDictTypeService extends IService<SysDictTypeEntity> {

    PageUtils<SysDictTypeEntity> queryPage(Map<String, Object> params);

    boolean saveDictType(SysDictTypeReqVo sysDictTypeVo);

    boolean updateDictTypeById(SysDictTypeReqVo sysDictTypeVo);

    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);
}

