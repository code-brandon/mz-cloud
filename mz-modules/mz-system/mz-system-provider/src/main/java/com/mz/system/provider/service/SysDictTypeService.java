package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictTypeEntity;
import com.mz.system.model.vo.req.SysDictTypeReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;

import java.util.Map;

/**
 * 字典类型表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysDictTypeService extends IService<SysDictTypeEntity> {

    /**
     * 分页查询字典类型
     *
     * @param params 分页参数
     * @return 分页数据
     */
    PageUtils<SysDictTypeEntity> queryPage(Map<String, Object> params);

    /**
     * 保存字典类型
     *
     * @param sysDictTypeVo 实体对象
     * @return true：成功，false：失败
     */
    boolean saveDictType(SysDictTypeReqVo sysDictTypeVo);

    /**
     * 根据字典类型ID修改字典类型
     *
     * @param sysDictTypeVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateDictTypeById(SysDictTypeReqVo sysDictTypeVo);

    /**
     * 修改字典类型状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);
}

