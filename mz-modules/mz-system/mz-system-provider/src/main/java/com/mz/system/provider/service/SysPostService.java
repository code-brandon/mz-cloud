package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysPostEntity;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysPostReqVo;

import java.util.Map;

/**
 * 岗位信息表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysPostService extends IService<SysPostEntity> {

    PageUtils<SysPostEntity> queryPage(Map<String, Object> params, SysPostReqVo sysPostReqVo);

    /**
     * 更新岗位状态
     *
     * @param idAndStatusReqVo 实体类
     * @return true：成功，false：失败
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);

    /**
     * 根据岗位ID更新岗位
     * @param sysPostReqVo 实体类
     * @return true：成功，false：失败
     */
    boolean updatePostById(SysPostReqVo sysPostReqVo);

    /**
     * 保存岗位
     * @param sysPostReqVo 实体类
     * @return true：成功，false：失败
     */
    boolean savePost(SysPostReqVo sysPostReqVo);
}

