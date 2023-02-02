package com.mz.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * What -- 通用树工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: TreeUtils
 * @CreateTime 2023/2/1 19:05
 */
public class TreeUtils {

    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表<br>
     * {@link <a>https://www.bilibili.com/video/BV1du411D7v4/?spm_id_from=333.999.0.0&vd_source=3f03af1f438bd753bf68f0f97e24fb72</a>} 评论区Map方式
     *
     * @param nodes 树形节点列表
     * @param flg 生成树的两种实现，<br> false：递归常用方式（据说时间复杂度高），<br> true：map方式（据说是空间换时间）
     * @param <T>   节点类型
     * @return 树形结构列表
     */
    public static <T extends TreeNode<?>> List<T> generateTrees(List<T> nodes,boolean flg) {
        List<T> roots = new ArrayList<>();

        if (!flg) {
            for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
                T node = ite.next();
                if (node.root()) {
                    roots.add(node);
                    // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                    ite.remove();
                }
            }
            if (!roots.isEmpty()) {
                roots.parallelStream().forEach(r -> {
                    setChildren(r, nodes);
                });
            }else {
                nodes.parallelStream().forEach(r -> {
                    setChildren(r, nodes);
                });
                return nodes;
            }

        }else {
            Map<Object, T > id2Node = new LinkedHashMap<>(Math.max((int) (nodes.size() / .75f) + 1, 16));
            nodes.forEach(e -> id2Node.put(e.id(), e));
            id2Node.forEach((id, node) -> {
                T parentNode = id2Node.get(node.parentId());
                if (parentNode != null) {
                    List children = parentNode.getChildren();
                    if (children == null) {
                        children = new ArrayList<T>();
                        parentNode.setChildren(children);
                    }
                    children.add(node);
                } else {
                    roots.add(node);
                }
            });
        }
        return roots;
    }

    /**
     * 从所有节点列表中查找并设置parent的所有子节点
     *
     * @param parent 父节点
     * @param nodes  所有树节点列表
     */
    @SuppressWarnings("all")
    public static <T extends TreeNode> void setChildren(T parent, List<T> nodes) {
        List<T> children = new ArrayList<>();
        Object parentId = parent.id();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (Objects.equals(node.parentId(), parentId)) {
                children.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        // 如果孩子为空，则直接返回,否则继续递归设置孩子的孩子
        if (children.isEmpty()) {
            return;
        }
        parent.setChildren(children);
        children.parallelStream().forEach(m -> {
            // 递归设置子节点
            setChildren(m, nodes);
        });
    }

    /**
     * 获取指定树节点下的所有叶子节点
     *
     * @param parent 父节点
     * @param <T>    实际节点类型
     * @return 叶子节点
     */
    public static <T extends TreeNode<?>> List<T> getLeafs(T parent) {
        List<T> leafs = new ArrayList<>();
        fillLeaf(parent, leafs);
        return leafs;
    }

    /**
     * 将parent的所有叶子节点填充至leafs列表中
     *
     * @param parent 父节点
     * @param leafs  叶子节点列表
     * @param <T>    实际节点类型
     */
    @SuppressWarnings("all")
    public static <T extends TreeNode> void fillLeaf(T parent, List<T> leafs) {
        List<T> children = parent.getChildren();
        // 如果节点没有子节点则说明为叶子节点
        if (CollectionUtils.isEmpty(children)) {
            leafs.add(parent);
            return;
        }
        // 递归调用子节点，查找叶子节点
        for (T child : children) {
            fillLeaf(child, leafs);
        }
    }


    /**
     * 树节点父类，所有需要使用{@linkplain TreeUtils}工具类形成树形结构等操作的节点都需要实现该接口
     *
     * @param <T> 节点id类型
     */
    public interface TreeNode<T> {
        /**
         * 获取节点id
         *
         * @return 树节点id
         */
        T id();

        /**
         * 获取该节点的父节点id
         *
         * @return 父节点id
         */
        T parentId();

        /**
         * 是否是根节点
         *
         * @return true：根节点
         */
        boolean root();

        /**
         * 设置节点的子节点列表
         *
         * @param children 子节点
         */
        void setChildren(List<? extends TreeNode<T>> children);

        /**
         * 获取所有子节点
         *
         * @return 子节点列表
         */
        List<? extends TreeNode<T>> getChildren();
    }
}