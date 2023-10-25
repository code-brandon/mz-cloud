package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysGatewayRouteEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 网关路由表
 * 
 * @author 小政同学 it_xiaozheng@163.com
 * @email 1911298402@qq.com
 * @date 2023-09-25 19:31:07
 */
@Mapper
@Repository
public interface SysGatewayRouteDao extends BaseMapper<SysGatewayRouteEntity> {
	
}
