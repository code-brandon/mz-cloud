package com.mz.common.captcha.config;

import cloud.tianai.captcha.common.exception.ImageCaptchaException;
import cloud.tianai.captcha.generator.ImageCaptchaGenerator;
import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cloud.tianai.captcha.spring.application.DefaultImageCaptchaApplication;
import cloud.tianai.captcha.spring.autoconfiguration.ImageCaptchaProperties;
import cloud.tianai.captcha.spring.store.CacheStore;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.ImageCaptchaValidator;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.mz.common.captcha.constant.XzCaptchaTypeConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 自定义 图片验证码应用程序
 * @ClassName: XzCaptchaConstant
 * @Author: 小政同学  Mail：it_xiaozheng@163.com
 * @date: 2023/4/21 16:33
 */
@Slf4j
public class XzImageCaptchaApplication extends DefaultImageCaptchaApplication {
    public XzImageCaptchaApplication(ImageCaptchaGenerator template, ImageCaptchaValidator imageCaptchaValidator, CacheStore cacheStore, ImageCaptchaProperties prop) {
        super(template, imageCaptchaValidator, cacheStore, prop);
    }


    @Override
    public CaptchaResponse<ImageCaptchaVO> generateCaptcha(String type) {
        ImageCaptchaInfo slideImageInfo = getImageCaptchaTemplate().generateCaptchaImage(type);
        if (XzCaptchaTypeConstant.IMAGE_INPUT_EASY.equals(type)) {
            if (slideImageInfo == null) {
                // 要是生成失败
                throw new ImageCaptchaException("生成滑块验证码失败，验证码生成为空");
            }
            // 生成ID
            String id = generatorId();
            log.debug("生成的验证码ID：{}", id);
            // 生成校验数据
            Map<String, Object> validData = new HashMap<>();
            validData.put("text", slideImageInfo.getData());
            // 存到缓存里
            cacheVerification(id, slideImageInfo.getType(), validData);
            ImageCaptchaVO verificationVO = new ImageCaptchaVO();
            verificationVO.setBackgroundImage(slideImageInfo.getBackgroundImage());
            verificationVO.setSliderImage(slideImageInfo.getSliderImage());
            verificationVO.setBackgroundImageWidth(slideImageInfo.getBgImageWidth());
            verificationVO.setBackgroundImageHeight(slideImageInfo.getBgImageHeight());
            verificationVO.setSliderImageWidth(slideImageInfo.getSliderImageWidth());
            verificationVO.setSliderImageHeight(slideImageInfo.getSliderImageHeight());
            verificationVO.setData(slideImageInfo.getData());
            return CaptchaResponse.of(id, verificationVO);
        }
        return afterGenerateSliderCaptcha(slideImageInfo);
    }

    @Override
    public boolean matching(String id, ImageCaptchaTrack imageCaptchaTrack) {
        log.debug("验证请求携带的验证码ID：{}", id);
        Map<String, Object> cachePercentage = getVerification(id);
        if (cachePercentage == null) {
            return false;
        }
        log.debug("通过验证码ID从缓存中获取的数据：{}", cachePercentage);
        String text = (String) cachePercentage.getOrDefault("text", "");
        if (StringUtils.isBlank(text)) {
          return  super.getImageCaptchaValidator().valid(imageCaptchaTrack, cachePercentage);
        }
        log.debug("自定义携带的扩展数据：{}", imageCaptchaTrack.getData());
        String trackData = (String) imageCaptchaTrack.getData();

        return text.equals(trackData);
    }
}
