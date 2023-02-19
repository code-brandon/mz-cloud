package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.common.utils.MzUtils;
import com.mz.system.model.entity.SysConfigEntity;
import com.mz.system.model.vo.req.SysConfigReqVo;
import com.mz.system.provider.dao.SysConfigDao;
import com.mz.system.provider.service.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params, SysConfigReqVo sysConfigReqVo) {
        IPage<SysConfigEntity> page = this.page(
                new Query<SysConfigEntity>().getPage(params),
                Wrappers.<SysConfigEntity>lambdaQuery()
                        .eq(MzUtils.notEmpty(sysConfigReqVo.getConfigKey()), SysConfigEntity::getConfigKey, sysConfigReqVo.getConfigKey())
                        .eq(MzUtils.notEmpty(sysConfigReqVo.getConfigType()), SysConfigEntity::getConfigType, sysConfigReqVo.getConfigType())
                        .likeRight(MzUtils.notEmpty(sysConfigReqVo.getConfigName()), SysConfigEntity::getConfigName, sysConfigReqVo.getConfigName())
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean saveConfig(SysConfigReqVo sysConfigReqVo) {
        SysConfigEntity sysConfigEntity = BeanUtil.copyProperties(sysConfigReqVo, SysConfigEntity.class);
        return super.save(sysConfigEntity);
    }

    @Override
    public boolean updateConfigById(SysConfigReqVo sysConfigReqVo) {
        SysConfigEntity sysConfigEntity = BeanUtil.copyProperties(sysConfigReqVo, SysConfigEntity.class);
        return super.updateById(sysConfigEntity);
    }

}