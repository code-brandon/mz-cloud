package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysConfigEntity;
import com.mz.system.model.vo.req.SysConfigReqVo;

import java.util.Map;

/**
 * 参数配置表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    /**
     * 分页查询配置
     * @param params  分页参数
     * @param sysConfigReqVo 查询条件
     * @return 分页信息
     */
    PageUtils queryPage(Map<String, Object> params, SysConfigReqVo sysConfigReqVo);

    /**
     * 保存配置信息
     * @param sysConfigReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean saveConfig(SysConfigReqVo sysConfigReqVo);

    /**
     * 修改配置信息
     * @param sysConfigReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateConfigById(SysConfigReqVo sysConfigReqVo);
}

