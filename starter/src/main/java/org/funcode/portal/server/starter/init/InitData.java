/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.starter.init;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.security.repository.IBasicAuthorityRepository;
import org.funcode.portal.server.common.core.security.repository.IRoleRepository;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.funcode.portal.server.common.core.util.ClassUtil;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.funcode.portal.server.common.domain.security.Role;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class InitData {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IBasicAuthorityRepository basicAuthorityRepository;
    private final ApplicationConfig applicationConfig;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @DependsOn("flywayInitStarter")
    public void init() {
        long userCount = userRepository.count();
        if (userCount > 0) {
            log.info("已经完成过初始化...直接启动...");
            return;
        }
        log.info("=======开始初始化数据...=========================================================");
        // 权限初始化
        Set<BasicAuthority> authorities = new HashSet<>();
        // 包下面的类
        Set<Class<?>> clazzs = ClassUtil.getClasses("org.funcode.portal.server");
        for (Class<?> clazz : clazzs) {
            if (!clazz.isAnnotationPresent(RestController.class) && !clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }
            if (clazz.isAnnotationPresent(PreAuthorize.class)) {
                // 获取类上的注解
                PreAuthorize clazzPreAuthorizeAnno = clazz.getAnnotation(PreAuthorize.class);
                String clazzPreAuthorizeAnnoValue = clazzPreAuthorizeAnno.value();
                String authority = extractAuthority(clazzPreAuthorizeAnnoValue);
                if (StringUtils.isNotBlank(authority) && !StringUtils.startsWith(authority, "ROLE_")) {
                    BasicAuthority clazzBasicAuthority = BasicAuthority.builder().authorityKey(authority).build();
                    if (clazz.isAnnotationPresent(Tag.class)) {
                        // 获取类上的注解
                        Tag clazzTagAnno = clazz.getAnnotation(Tag.class);
                        String clazzTagName = clazzTagAnno.name();
                        String clazzTagDescription = clazzTagAnno.description();
                        clazzBasicAuthority.setAuthorityName(clazzTagName);
                        clazzBasicAuthority.setDescription(clazzTagDescription);
                    }
                    authorities.add(clazzBasicAuthority);
                }
            }

            // 获取方法上的注解
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PreAuthorize.class)) {
                    // 获取方法上的注解
                    PreAuthorize methodPreAuthorizeAnno = method.getAnnotation(PreAuthorize.class);
                    String methodPreAuthorizeAnnoValue = methodPreAuthorizeAnno.value();
                    String authority = extractAuthority(methodPreAuthorizeAnnoValue);
                    if (StringUtils.isNotBlank(authority) && !StringUtils.startsWith(authority, "ROLE_")) {
                        BasicAuthority methodBasicAuthority = BasicAuthority.builder().authorityKey(authority).build();
                        if (method.isAnnotationPresent(Operation.class)) {
                            // 获取方法上的注解
                            Operation methodOperationAnno = method.getAnnotation(Operation.class);
                            String methodOperationSummary = methodOperationAnno.summary();
                            String methodOperationDescription = methodOperationAnno.description();
                            methodBasicAuthority.setAuthorityName(methodOperationSummary);
                            methodBasicAuthority.setDescription(methodOperationDescription);
                        }
                        authorities.add(methodBasicAuthority);
                    }
                }
            }
        }
        basicAuthorityRepository.saveAllAndFlush(authorities);
        // 角色初始化
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().roleName("管理员").roleKey("admin").basicAuthorities(authorities).build());
        roles.add(Role.builder().roleName("学员").roleKey("student").build());
        roleRepository.saveAllAndFlush(roles);
        // 人员初始化
        User adminUser = User.builder()
                .username(applicationConfig.getSecurity().adminUsername())
                .password(passwordEncoder.encode(applicationConfig.getSecurity().adminPassword()))
                .realName("管理员")
                .nickName("管理员")
                .roles(roles)
                .basicAuthorities(authorities)
                .build();
        userRepository.saveAndFlush(adminUser);
        log.info("=======初始化数据结束=========================================================");
    }

    /**
     * 提取 hasAuthority 值的简单正则表达式
     */
    private static String extractAuthority(String value) {
        String authority = null;
        String regex = "hasAuthority\\('(.*?)'\\)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            authority = matcher.group(1);
        }
        return authority;
    }
}
