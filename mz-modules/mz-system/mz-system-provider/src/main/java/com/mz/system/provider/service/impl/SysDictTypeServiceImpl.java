package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysDictTypeEntity;
import com.mz.system.model.vo.req.SysDictTypeReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.provider.dao.SysDictTypeDao;
import com.mz.system.provider.service.SysDictTypeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService {

    @Override
    public PageUtils<SysDictTypeEntity> queryPage(Map<String, Object> params) {
        IPage<SysDictTypeEntity> page = super.page(
                new Query<SysDictTypeEntity>().getPage(params),
                Wrappers.<SysDictTypeEntity>lambdaQuery().orderByDesc(SysDictTypeEntity::getDictId)
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean saveDictType(SysDictTypeReqVo sysDictTypeVo) {
        SysDictTypeEntity sysDictTypeEntity = BeanUtil.copyProperties(sysDictTypeVo, SysDictTypeEntity.class);
        return super.save(sysDictTypeEntity);
    }

    @Override
    public boolean updateDictTypeById(SysDictTypeReqVo sysDictTypeVo) {
        SysDictTypeEntity sysDictTypeEntity = BeanUtil.copyProperties(sysDictTypeVo, SysDictTypeEntity.class);
        return super.updateById(sysDictTypeEntity);
    }

    @Override
    public boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo) {
        SysDictTypeEntity sysDictTypeEntity = new SysDictTypeEntity();
        sysDictTypeEntity.setDictId(idAndStatusReqVo.getSysId());
        sysDictTypeEntity.setStatus(idAndStatusReqVo.getStatus());
        return super.updateById(sysDictTypeEntity);
    }

}