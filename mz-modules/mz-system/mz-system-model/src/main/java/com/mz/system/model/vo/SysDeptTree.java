package com.mz.system.model.vo;

import com.mz.common.constant.Constant;
import com.mz.common.utils.TreeUtils;
import com.mz.system.model.entity.SysDeptEntity;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model.vo
 * @ClassName: SysDeptTree
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/1 22:26
 */
@Data
public class SysDeptTree extends SysDeptEntity implements TreeUtils.TreeNode<Long> {


    private List<SysDeptTree> children;
    /**
     * 获取节点id
     *
     * @return 树节点id
     */
    @Override
    public Long id() {
        return getDeptId();
    }

    /**
     * 获取该节点的父节点id
     *
     * @return 父节点id
     */
    @Override
    public Long parentId() {
        return getParentId();
    }

    /**
     * 是否是根节点
     *
     * @return true：根节点
     */
    @Override
    public boolean root() {
        return Objects.equals(parentId(), Constant.ROOT_NODE);
    }


    /**
     * 获取所有子节点
     *
     * @return 子节点列表
     */
    @Override
    public List<SysDeptTree> getChildren() {
        return children;
    }

    /**
     * 设置节点的子节点列表
     *
     * @param children 子节点
     */
    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
