package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.constant.MzConstant;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.common.utils.MzUtils;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.model.vo.req.SysDictDataReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysDictDataSearchVo;
import com.mz.system.provider.dao.SysDictDataDao;
import com.mz.system.provider.service.SysDictDataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

    @Override
    public PageUtils<SysDictDataEntity> queryPage(Map<String, Object> params, SysDictDataSearchVo dictDataSearchVo) {

        IPage<SysDictDataEntity> page = this.page(
                new Query<SysDictDataEntity>().getPage(params),
                Wrappers.<SysDictDataEntity>lambdaQuery()
                        .eq(SysDictDataEntity::getDictType, dictDataSearchVo.getDictType())
                        .eq(MzUtils.notEmpty(dictDataSearchVo.getDictLabel()), SysDictDataEntity::getDictLabel, dictDataSearchVo.getDictLabel())
                        .eq(MzUtils.notEmpty(dictDataSearchVo.getDictValue()), SysDictDataEntity::getDictValue, dictDataSearchVo.getDictValue())
                        .orderByAsc(SysDictDataEntity::getDictSort)
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean saveDictData(SysDictDataReqVo sysDictDataVo) {
        SysDictDataEntity sysDictDataEntity = BeanUtil.copyProperties(sysDictDataVo, SysDictDataEntity.class);
        return super.save(sysDictDataEntity);
    }

    @Override
    public boolean updateDictDataById(SysDictDataReqVo sysDictDataVo) {
        SysDictDataEntity sysDictDataEntity = BeanUtil.copyProperties(sysDictDataVo, SysDictDataEntity.class);
        return super.updateById(sysDictDataEntity);
    }

    @Override
    public boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo) {
        SysDictDataEntity sysDictDataEntity = new SysDictDataEntity();
        sysDictDataEntity.setDictCode(idAndStatusReqVo.getSysId());
        sysDictDataEntity.setStatus(idAndStatusReqVo.getStatus());
        return super.updateById(sysDictDataEntity);
    }

    @Override
    public List<SysDictDataEntity> listByDictType(String dictType) {

        return list(Wrappers.<SysDictDataEntity>lambdaQuery()
                .eq(SysDictDataEntity::getDictType, dictType)
                .eq(SysDictDataEntity::getStatus, MzConstant.DICT_NORMAL)
                .orderByAsc(SysDictDataEntity::getDictSort)
        );
    }

}