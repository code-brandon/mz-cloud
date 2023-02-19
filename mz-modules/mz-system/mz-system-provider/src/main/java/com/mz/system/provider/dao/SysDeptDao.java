package com.mz.system.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.system.model.entity.SysDeptEntity;
import com.mz.system.model.vo.SysDeptTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门表
 * 
 * @author 小政同学 QQ:xiaozheng666888@qq.com
 * @email 1911298402@qq.com
 * @date 2021-11-13 22:14:36
 */
@Mapper
@Repository
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /**
     * 按部门名称和状态获取全部
     * @param deptName
     * @param status
     * @return
     */
    public List<SysDeptTree> getAllByDeptNameAndStatus(@Param("deptName") String deptName, @Param("status") String status);
	
}
