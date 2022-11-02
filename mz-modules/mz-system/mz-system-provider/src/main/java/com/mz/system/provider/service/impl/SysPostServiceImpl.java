package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysPostEntity;
import com.mz.system.provider.dao.SysPostDao;
import com.mz.system.provider.service.SysPostService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {

    @Override
    public PageUtils<SysPostEntity> queryPage(Map<String, Object> params) {
        IPage<SysPostEntity> page = this.page(
                new Query<SysPostEntity>().getPage(params),
                new QueryWrapper<SysPostEntity>()
        );
        return new PageUtils<>(page);
    }

}