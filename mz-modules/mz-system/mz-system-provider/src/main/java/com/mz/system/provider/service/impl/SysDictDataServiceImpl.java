package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.provider.dao.SysDictDataDao;
import com.mz.system.provider.service.SysDictDataService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictDataService")
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDictDataEntity> page = this.page(
                new Query<SysDictDataEntity>().getPage(params),
                new QueryWrapper<SysDictDataEntity>()
        );
        return new PageUtils(page);
    }

}