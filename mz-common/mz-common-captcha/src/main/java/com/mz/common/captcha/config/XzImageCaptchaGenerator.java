package com.mz.common.captcha.config;

import cloud.tianai.captcha.generator.AbstractImageCaptchaGenerator;
import cloud.tianai.captcha.generator.ImageTransform;
import cloud.tianai.captcha.generator.common.model.dto.GenerateParam;
import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import java.awt.*;

/**
 * @Description: 自定义 验证码生成器
 * @ClassName: XzCaptchaConstant
 * @Author: 小政同学  Mail：it_xiaozheng@163.com
 * @date: 2023/4/21 16:33
 */
public class XzImageCaptchaGenerator extends AbstractImageCaptchaGenerator {
    public XzImageCaptchaGenerator(ImageCaptchaResourceManager resourceManager, ImageTransform imageTransform) {
        setImageResourceManager(resourceManager);
        setImageTransform(imageTransform);
    }

    @Override
    protected void doInit(boolean initDefaultResource) {

    }

    @Override
    protected ImageCaptchaInfo doGenerateCaptchaImage(GenerateParam param) {
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        String base64 = specCaptcha.toBase64();
        ImageCaptchaInfo imageCaptchaInfo = new ImageCaptchaInfo();
        imageCaptchaInfo.setBackgroundImage(base64);
        imageCaptchaInfo.setBgImageHeight(48);
        imageCaptchaInfo.setBgImageWidth(130);
        imageCaptchaInfo.setData(specCaptcha.text());
        return imageCaptchaInfo;
    }
}
