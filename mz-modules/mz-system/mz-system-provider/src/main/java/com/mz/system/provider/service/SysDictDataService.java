package com.mz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mz.common.mybatis.utils.PageUtils;
import com.mz.system.model.entity.SysDictDataEntity;
import com.mz.system.model.vo.req.SysDictDataReqVo;
import com.mz.system.model.vo.req.SysIdAndStatusReqVo;
import com.mz.system.model.vo.search.SysDictDataSearchVo;

import java.util.List;
import java.util.Map;

/**
 * 字典数据表
 *
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
public interface SysDictDataService extends IService<SysDictDataEntity> {

    /**
     * 分页查询字典数据
     *
     * @param params 分页参数
     * @return 分页信息
     */
    PageUtils<SysDictDataEntity> queryPage(Map<String, Object> params, SysDictDataSearchVo dictDataSearchReqVo);

    /**
     * 保存字典数据
     *
     * @param sysDictDataVo 实体对象
     * @return true：成功，false：失败
     */
    boolean saveDictData(SysDictDataReqVo sysDictDataVo);

    /**
     * 根据字典码修改字典数据
     *
     * @param sysDictDataVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateDictDataById(SysDictDataReqVo sysDictDataVo);

    /**
     * 修改字典数据状态
     *
     * @param idAndStatusReqVo 实体对象
     * @return true：成功，false：失败
     */
    boolean updateStatus(SysIdAndStatusReqVo idAndStatusReqVo);

    /**
     * 通过字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictDataEntity> listByDictType(String dictType);
}

