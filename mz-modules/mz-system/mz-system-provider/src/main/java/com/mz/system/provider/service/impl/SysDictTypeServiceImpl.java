package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysDictTypeEntity;
import com.mz.system.provider.dao.SysDictTypeDao;
import com.mz.system.provider.service.SysDictTypeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDictTypeEntity> page = this.page(
                new Query<SysDictTypeEntity>().getPage(params),
                new QueryWrapper<SysDictTypeEntity>()
        );
        return new PageUtils(page);
    }

}