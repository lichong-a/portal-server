/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.provider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.security.domain.WechatAuthenticationToken;
import org.funcode.portal.server.common.core.security.service.IUserService;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WeChatAuthenticationProvider implements AuthenticationProvider {
    private final RestTemplate restTemplate;
    private final ApplicationConfig applicationConfig;
    private final IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        WechatAuthenticationToken wechatAuthenticationToken = (WechatAuthenticationToken) authentication;
        String loginCode = wechatAuthenticationToken.getPrincipal().toString();
        log.info("loginCode is {}", loginCode);
        //获取openId
        String wechatLoginUrl = applicationConfig.wechatLoginUrl(loginCode);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(wechatLoginUrl, String.class);
        JsonObject jsonObject = new Gson().fromJson(responseEntity.getBody(), JsonObject.class);
        log.info(jsonObject.getAsString());
        String openId = jsonObject.get("openid").getAsString();
        if (StringUtils.isBlank(openId)) {
            throw new BadCredentialsException("WeChat get openId error");
        }
        User user = userService.findByWechatId(openId);
        if (user == null) {
            // 人员不存在
            throw new BadCredentialsException("user not exists");
        }
        return getAuthenticationToken(openId, user, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public WechatAuthenticationToken getAuthenticationToken(String openId, Object principal, Collection<? extends GrantedAuthority> authorities) {
        WechatAuthenticationToken authenticationToken = new WechatAuthenticationToken(openId, principal, authorities);
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("principal", authenticationToken.getPrincipal());
        authenticationToken.setDetails(linkedHashMap);
        return authenticationToken;
    }
}
