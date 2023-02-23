package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.model.vo.req.SysDictDataReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysDictDataSearchVo;

import java.util.List;
import java.util.Map;

/**
 * 字典数据表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysDictDataService extends IService<SysDictDataEntity> {

    PageUtils<SysDictDataEntity> queryPage(Map<String, Object> params, SysDictDataSearchVo dictDataSearchReqVo);

    boolean saveDictData(SysDictDataReqVo sysDictDataVo);

    boolean updateDictDataById(SysDictDataReqVo sysDictDataVo);

    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);

    List<SysDictDataEntity> listByDictType(String dictType);
}

