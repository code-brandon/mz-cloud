package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysOperLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 操作日志记录
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysOperLogDao extends BaseMapper<SysOperLogEntity> {
	
}
