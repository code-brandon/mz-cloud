package com.mz.common.captcha.config;

import cloud.tianai.captcha.generator.ImageCaptchaGenerator;
import cloud.tianai.captcha.generator.ImageCaptchaGeneratorProvider;
import cloud.tianai.captcha.generator.ImageTransform;
import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import com.mz.common.captcha.constant.XzCaptchaTypeConstant;
import org.springframework.stereotype.Component;

/**
 * @Description: 自定义 ImageCaptchaGenerator 提供者
 * @ClassName: XzCaptchaConstant
 * @Author: 小政同学  Mail：it_xiaozheng@163.com
 * @date: 2023/4/21 16:33
 */
@Component
public class XzImageCaptchaGeneratorProvider implements ImageCaptchaGeneratorProvider {
    @Override
    public ImageCaptchaGenerator get(ImageCaptchaResourceManager resourceManager, ImageTransform imageTransform) {
        return new XzImageCaptchaGenerator(resourceManager, imageTransform);
    }

    @Override
    public String getType() {
        return XzCaptchaTypeConstant.IMAGE_INPUT_EASY;
    }
}