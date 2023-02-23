package com.mz.system.model.vo;

import com.mz.common.constant.MzConstant;
import com.mz.common.utils.TreeUtils;
import com.mz.system.model.entity.SysMenuEntity;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.system.model
 * @ClassName: SysMenuTree
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2023/2/10 10:27
 */
@Data
public class SysMenuTree extends SysMenuEntity implements TreeUtils.TreeNode<Long> {

    private List<SysMenuTree> children;

    /**
     * 获取节点id
     *
     * @return 树节点id
     */
    @Override
    public Long id() {
        return getMenuId();
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
        return Objects.equals(parentId(), MzConstant.ROOT_NODE);
    }

    /**
     * 设置节点的子节点列表
     *
     * @param children 子节点
     */
    @Override
    public void setChildren(List<? extends TreeUtils.TreeNode<Long>> children) {

        this.children = (List<SysMenuTree>) children;
    }

    /**
     * 获取所有子节点
     *
     * @return 子节点列表
     */
    @Override
    public List<SysMenuTree> getChildren() {
        return  children;
    }
}
