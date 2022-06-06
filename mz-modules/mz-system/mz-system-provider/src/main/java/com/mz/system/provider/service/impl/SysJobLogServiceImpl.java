package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysJobLogEntity;
import com.mz.system.provider.dao.SysJobLogDao;
import com.mz.system.provider.service.SysJobLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysJobLogService")
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogDao, SysJobLogEntity> implements SysJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysJobLogEntity> page = this.page(
                new Query<SysJobLogEntity>().getPage(params),
                new QueryWrapper<SysJobLogEntity>()
        );
        return new PageUtils(page);
    }

}