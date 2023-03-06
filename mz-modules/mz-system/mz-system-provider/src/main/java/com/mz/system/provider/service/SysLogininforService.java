package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.dto.SysLogininforDto;
import com.mz.system.model.entity.SysLogininforEntity;

import java.util.Map;

/**
 * 系统访问记录
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysLogininforService extends IService<SysLogininforEntity> {

    /**
     * 分页查询访问记录
     *
     * @param params 分页参数
     * @return 分页数据
     */
    PageUtils<SysLogininforEntity> queryPage(Map<String, Object> params);

    /**
     * 保存访问记录
     *
     * @param sysLogininfor 实体对象
     * @return true：成功，false：失败
     */
    boolean saveLogininfor(SysLogininforDto sysLogininfor);
}

