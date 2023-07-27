package com.mz.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

/**
 * 获取RSA公钥接口
 * Created by macro on 2020/6/19.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
//必须配置
@SessionAttributes("authorizationRequest")
@RequestMapping("/oauth")
public class MzOauthController {

    // private final ResourceServerTokenServices resourceServerTokenServices;

    private final TokenStore tokenStore;

    @GetMapping("/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        //自定义页面名字，resources\templates\auth-grant.html
        view.setViewName("auth-grant");
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());
        return view;
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        Object attribute = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        Optional.ofNullable(attribute).ifPresent(c -> log.info("获取session：{}", c));
        return "auth-login";
    }

    @PostMapping(value = "/check_token")
    @ResponseBody
    public Map<String, ?> checkToken(@RequestParam("token") String value) {

        OAuth2AccessToken accessToken = tokenStore.readAccessToken(value);
        if (accessToken  == null) {
            throw new InvalidTokenException("Token was not recognised");
        }

        if (accessToken .isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }
        OAuth2Authentication authentication = tokenStore.readAuthentication(value);


        Map<String, Object> response = (Map<String, Object>)new DefaultAccessTokenConverter().convertAccessToken(accessToken, authentication);

        response.put(UserAuthenticationConverter.USERNAME, authentication.getPrincipal());

        // gh-1070
        response.put("active", true);    // Always true if token exists and not expired

        return response;
    }


}