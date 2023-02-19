package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.dto.SysOperLogDto;
import com.mz.system.model.entity.SysOperLogEntity;
import com.mz.system.provider.dao.SysOperLogDao;
import com.mz.system.provider.service.SysOperLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOperLogService")
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogDao, SysOperLogEntity> implements SysOperLogService {

    @Override
    public PageUtils<SysOperLogEntity> queryPage(Map<String, Object> params) {
        IPage<SysOperLogEntity> page = this.page(
                new Query<SysOperLogEntity>().getPage(params),
                new QueryWrapper<SysOperLogEntity>()
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean saveOperLog(SysOperLogDto sysOperLog) {
        SysOperLogEntity sysOperLogEntity = BeanUtil.copyProperties(sysOperLog, SysOperLogEntity.class);
        return save(sysOperLogEntity);
    }

}