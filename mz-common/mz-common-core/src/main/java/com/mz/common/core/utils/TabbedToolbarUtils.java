/*
 * 项目名： gulimall-common
 * 文件名： TabbedToolbarUtils.java
 * 模块说明：
 * 修改历史:
 * 2021-9-16 0:34:10 - 小政同学i丷 - 创建.
 */

package com.mz.common.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName TabbedToolbarUtils
 * @CreateTime 2021/9/16 0:13
 */
public class TabbedToolbarUtils {
    /**
     *
     * @param totalPage 总页数
     * @param currentPage 当前页
     * @param displayNumber 每次显示页数
     * @return
     */
    public static Toolbar compute(Long totalPage, Long currentPage,Long displayNumber) {

        Toolbar toolbar = new Toolbar();
        Long begin;//开始位置
        Long end; //结束位置
        //1.2.1要显示displayNumber个页码
        if(totalPage <= displayNumber){
            //总页不够displayNumber页
            begin = 1L;
            end = totalPage;
        }else{
            //总页超过10页
            long t = displayNumber / 2;
            begin = currentPage - (displayNumber - t);
            end = currentPage + t;
            //如果前边不够，后边补齐
            if(begin < 1){
                begin = 1L;
                end = begin + displayNumber;
            }
            //如果后边不足，前边补齐
            if (end > totalPage){
                end = totalPage;
                begin = end -displayNumber;
            }
        }
        List<Long> numberOf = new ArrayList<>();
        for (; begin <= end; begin++) {
            numberOf.add(begin);
        }
        Map<String, Object> map = new HashMap<>();
        // 封装分页条开始页
        toolbar.setBegin(begin);
        // 封装分页条结束页
        toolbar.setEnd(end);
        // 封装分页条开始到结束的所有页
        toolbar.setNumberOf(numberOf);
        return toolbar;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Toolbar {
        /**
         * 开始位置
         */
        private Long begin;
        /**
         * 结束位置
         */
        private Long end;
        /**
         * 条数
         */
        private List<Long> numberOf;
    }


    public static void main(String[] args) {
        Toolbar compute = compute(2L, 0L, 2L);
        System.out.println("compute = " + compute);
    }
}
