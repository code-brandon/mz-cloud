package com.mz.auth.controller;


import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.autoconfiguration.ImageCaptchaProperties;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cn.hutool.core.lang.UUID;
import com.mz.common.constant.MzConstant;
import com.mz.common.core.entity.R;
import com.mz.common.redis.utils.MzRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private ImageCaptchaProperties imageCaptchaProperties;

    @Resource
    private ImageCaptchaApplication imageCaptchaApplication;

    @Resource
    private MzRedisUtil mzRedisUtil;

    @GetMapping("/gen")
    @ResponseBody
    public CaptchaResponse<ImageCaptchaVO> genCaptcha(HttpServletRequest request, @RequestParam(value = "type", required = false)String type) {
        if (StringUtils.isBlank(type)) {
            type = CaptchaTypeConstant.SLIDER;
        }
        return imageCaptchaApplication.generateCaptcha(type);
    }

    @PostMapping("/check")
    @ResponseBody
    public R<Map<String, Object>> checkCaptcha(@RequestParam("id") String id,
                                @RequestBody ImageCaptchaTrack imageCaptchaTrack,
                                HttpServletRequest request) {
        Map<String, Object> hashMap = new HashMap<>(2);
        if (imageCaptchaApplication.matching(id, imageCaptchaTrack)) {
            String uuid = UUID.randomUUID().toString();
            hashMap.put("flg", true);
            hashMap.put("code", uuid);
            String checkOkKey = imageCaptchaProperties.getPrefix().concat(MzConstant.CAPTCHA_OK_KEY).concat(id);
            // 存放五分钟
            mzRedisUtil.setCacheObject(checkOkKey, uuid, 5, TimeUnit.MINUTES);
        } else {
            hashMap.put("flg", false);
            hashMap.put("code", -1);
        }
        return R.ok(hashMap);
    }

    /**
     * 二次验证，一般用于机器内部调用，这里为了方便测试
     * @param id id
     * @return boolean
     */
    @GetMapping("/check2")
    @ResponseBody
    public boolean check2Captcha(@RequestParam("id") String id) {
        // 如果开启了二次验证
        if (imageCaptchaApplication instanceof SecondaryVerificationApplication) {
            return ((SecondaryVerificationApplication) imageCaptchaApplication).secondaryVerification(id);
        }
        return false;
    }
}

