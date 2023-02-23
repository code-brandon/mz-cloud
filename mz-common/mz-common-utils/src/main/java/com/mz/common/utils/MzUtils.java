package com.mz.common.utils;


import cn.hutool.core.util.ObjectUtil;
import com.mz.common.constant.MzConstant;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * What -- mz 工具类
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.mz.common.core.utils
 * @ClassName: MzUtils
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2022/10/29 1:08
 */
public class MzUtils {
    /**
     * @param colour  颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param type    样式代号：0无；1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    public static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }

    /**
     * @Description: 自定义validation校验
     * @Author: 小政同学  Mail：QQ:xiaozheng666888@qq.com
     * @date: 2022/12/6/006 16:08
     */
    public static void validate(Object obj) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()){
            Set<ConstraintViolation<Object>> validateResult = validatorFactory.getValidator().validate(obj, Default.class);
            if (!validateResult.isEmpty()) {
                throw new ConstraintViolationException(validateResult);
            }
        }
    }

    public static void validate(Object obj,Class<?>... groups) {
        try (ValidatorFactory  validatorFactory = Validation.buildDefaultValidatorFactory()){
            Set<ConstraintViolation<Object>> validateResult = validatorFactory.getValidator().validate(obj, groups);
            if (!validateResult.isEmpty()) {
                throw new ConstraintViolationException(validateResult);
            }
        }
    }

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return StringUtils.startsWithAny(link, MzConstant.HTTP, MzConstant.HTTPS);
    }

    /**
     * 判断指定对象是否为空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param value 被判断的对象
     * @return 是否为空，是空：true ;如果类型不支持，返回false
     * @since  hutool
     */

    public static <V> boolean isEmpty(V value) {
        if (ObjectUtil.isNull(value)) {
            return true;
        }
        return ObjectUtil.isEmpty(value);
    }

    public static <V> boolean notEmpty(V value) {
        return !isEmpty(value);
    }

}
