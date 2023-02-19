package com.mz.system.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.common.mybatis.utils.Query;
import com.mz.system.model.entity.SysPostEntity;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysPostReqVo;
import com.mz.system.provider.dao.SysPostDao;
import com.mz.system.provider.service.SysPostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {

    @Override
    public PageUtils<SysPostEntity> queryPage(Map<String, Object> params, SysPostReqVo sysPostReqVo) {
        IPage<SysPostEntity> page = super.page(
                new Query<SysPostEntity>().getPage(params),
                Wrappers.<SysPostEntity>lambdaQuery()
                        .eq(StringUtils.isNotEmpty(sysPostReqVo.getStatus()),SysPostEntity::getStatus, sysPostReqVo.getStatus())
                        .like(StringUtils.isNotEmpty(sysPostReqVo.getPostName()),SysPostEntity::getPostName, sysPostReqVo.getPostName())
                        .orderBy(Boolean.TRUE,Boolean.TRUE, SysPostEntity::getPostSort, SysPostEntity::getCreateTime)
        );
        return new PageUtils<>(page);
    }

    @Override
    public boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo) {
        SysPostEntity sysPostEntity = new SysPostEntity();
        sysPostEntity.setPostId(idAndStatusReqVo.getSysId());
        sysPostEntity.setStatus(idAndStatusReqVo.getStatus());
        return super.updateById(sysPostEntity);
    }

    @Override
    public boolean updatePostById(SysPostReqVo sysPostReqVo) {
        SysPostEntity sysPostEntity  = BeanUtil.copyProperties(sysPostReqVo, SysPostEntity.class, "status");
        return super.updateById(sysPostEntity);
    }

    @Override
    public boolean savePost(SysPostReqVo sysPostReqVo) {
        SysPostEntity sysPostEntity = BeanUtil.copyProperties(sysPostReqVo, SysPostEntity.class);
        return super.save(sysPostEntity);
    }

}