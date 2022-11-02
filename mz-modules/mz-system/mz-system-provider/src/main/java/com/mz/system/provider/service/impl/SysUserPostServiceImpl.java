package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysUserPostEntity;
import com.mz.system.provider.dao.SysUserPostDao;
import com.mz.system.provider.service.SysUserPostService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysUserPostService")
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostDao, SysUserPostEntity> implements SysUserPostService {

    @Override
    public PageUtils<SysUserPostEntity> queryPage(Map<String, Object> params) {
        IPage<SysUserPostEntity> page = this.page(
                new Query<SysUserPostEntity>().getPage(params),
                new QueryWrapper<SysUserPostEntity>()
        );
        return new PageUtils<>(page);
    }

}