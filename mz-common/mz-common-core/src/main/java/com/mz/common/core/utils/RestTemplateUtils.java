package com.mz.common.core.utils;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * What -- http请求工具类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: RestTemplateUtils
 * @CreateTime 2022/6/6 9:17
 */
@RequiredArgsConstructor
@Component
public class RestTemplateUtils {
    private final RestTemplate restTemplate;
    /**
     * get请求
     *
     * @param url       请求地址
     * @param headerMap 头部信息
     * @param resp      响应结果类型
     * @return
     */
    public  Object  get(String url, Map<String, String> headerMap, Class<?> resp) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, resp);
        return result.getBody();
    }

    /**
     * get请求
     * @param url
     * @param resp
     * @return ResponseEntity
     */
    public  ResponseEntity  resGet(String url, Class<?> resp) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3877.400 QQBrowser/10.8.4507.400");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, resp);
    }
    /**
     * post 请求
     *
     * @param url        请求地址
     * @param headerMap  头信息
     * @param jsonObject 请求参数 JSON
     * @return JSONObject
     */
    public JSONObject post(String url, Map<String, String> headerMap, JSONObject jsonObject) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(jsonObject, httpHeaders);
        JSONObject result = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        return result;
    }

    /**
     * post 请求
     * @param url
     * @param jsonObject
     * @return ResponseEntity
     */
    public ResponseEntity resPost(String url, JSONObject jsonObject) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3877.400 QQBrowser/10.8.4507.400");
        HttpEntity httpEntity = new HttpEntity(jsonObject, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity,JSONObject.class);
    }
}