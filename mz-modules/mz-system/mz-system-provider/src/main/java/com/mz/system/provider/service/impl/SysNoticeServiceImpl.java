package com.mz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysNoticeEntity;
import com.mz.system.provider.dao.SysNoticeDao;
import com.mz.system.provider.service.SysNoticeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysNoticeService")
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNoticeEntity> implements SysNoticeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysNoticeEntity> page = this.page(
                new Query<SysNoticeEntity>().getPage(params),
                new QueryWrapper<SysNoticeEntity>()
        );
        return new PageUtils(page);
    }

}