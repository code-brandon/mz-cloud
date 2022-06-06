package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysJobEntity;
import com.mz.system.provider.dao.SysJobDao;
import com.mz.system.provider.service.SysJobService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysJobService")
public class SysJobServiceImpl extends ServiceImpl<SysJobDao, SysJobEntity> implements SysJobService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysJobEntity> page = this.page(
                new Query<SysJobEntity>().getPage(params),
                new QueryWrapper<SysJobEntity>()
        );
        return new PageUtils(page);
    }

}