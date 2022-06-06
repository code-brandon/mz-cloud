package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysRoleDeptEntity;
import com.mz.system.provider.dao.SysRoleDeptDao;
import com.mz.system.provider.service.SysRoleDeptService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity> implements SysRoleDeptService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleDeptEntity> page = this.page(
                new Query<SysRoleDeptEntity>().getPage(params),
                new QueryWrapper<SysRoleDeptEntity>()
        );
        return new PageUtils(page);
    }

}