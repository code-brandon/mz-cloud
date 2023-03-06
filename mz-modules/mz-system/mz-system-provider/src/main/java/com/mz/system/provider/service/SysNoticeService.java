package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysNoticeEntity;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.req.SysNoticeReqVo;

import java.util.Map;

/**
 * 通知公告表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysNoticeService extends IService<SysNoticeEntity> {

    /**
     * 分页查询通知公告
     *
     * @param params 分页参数
     * @return 分页数据
     */
    PageUtils<SysNoticeEntity> queryPage(Map<String, Object> params);

    /**
     * 保存通知公告
     *
     * @param sysNoticeVo 实体对象
     * @return true：成功，false：失败
     */
    boolean saveNotice(SysNoticeReqVo sysNoticeVo);

    /**
     * 修改通知公告
     *
     * @param sysNoticeVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateNoticeById(SysNoticeReqVo sysNoticeVo);

    /**
     * 修改通知公告状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);
}

