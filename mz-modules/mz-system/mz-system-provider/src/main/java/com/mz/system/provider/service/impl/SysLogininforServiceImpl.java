package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysLogininforEntity;
import com.mz.system.provider.dao.SysLogininforDao;
import com.mz.system.provider.service.SysLogininforService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogininforService")
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforDao, SysLogininforEntity> implements SysLogininforService {

    @Override
    public PageUtils<SysLogininforEntity>  queryPage(Map<String, Object> params) {
        IPage<SysLogininforEntity> page = this.page(
                new Query<SysLogininforEntity>().getPage(params),
                new QueryWrapper<SysLogininforEntity>()
        );
        return new PageUtils<>(page);
    }

}