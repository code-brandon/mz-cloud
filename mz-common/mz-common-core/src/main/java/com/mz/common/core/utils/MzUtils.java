package com.mz.common.core.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
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
        try (ValidatorFactory  validatorFactory = Validation.buildDefaultValidatorFactory()){
            //先进行参数校验
            StringBuilder returnSb = new StringBuilder();
            Set<ConstraintViolation<Object>> validateResult = validatorFactory.getValidator().validate(obj, Default.class);
            if (!validateResult.isEmpty()) {
                for (ConstraintViolation<Object> cv : validateResult) {
                    returnSb.append(cv.getPropertyPath()).append(":").append(cv.getMessage()).append(";");
                }
                throw new ValidationException(returnSb.toString());
            }
        }
    }
}
