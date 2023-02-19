package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysNoticeEntity;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysNoticeReqVo;
import com.mz.system.provider.dao.SysNoticeDao;
import com.mz.system.provider.service.SysNoticeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysNoticeService")
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNoticeEntity> implements SysNoticeService {

    @Override
    public PageUtils<SysNoticeEntity> queryPage(Map<String, Object> params) {
        IPage<SysNoticeEntity> page = super.page(
                new Query<SysNoticeEntity>().getPage(params),
                new QueryWrapper<SysNoticeEntity>()
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean saveNotice(SysNoticeReqVo sysNoticeVo) {
        SysNoticeEntity sysNoticeEntity = BeanUtil.copyProperties(sysNoticeVo, SysNoticeEntity.class);
        return super.save(sysNoticeEntity);
    }

    @Override
    public boolean updateNoticeById(SysNoticeReqVo sysNoticeVo) {
        SysNoticeEntity sysNoticeEntity = BeanUtil.copyProperties(sysNoticeVo, SysNoticeEntity.class);
        return super.updateById(sysNoticeEntity);
    }

    @Override
    public boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo) {
        SysNoticeEntity sysNoticeEntity = new SysNoticeEntity();
        sysNoticeEntity.setNoticeId(idAndStatusReqVo.getSysId());
        sysNoticeEntity.setStatus(idAndStatusReqVo.getStatus());
        return super.updateById(sysNoticeEntity);
    }

}