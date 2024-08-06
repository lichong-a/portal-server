/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.starter.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.security.repository.IBasicAuthorityRepository;
import org.funcode.portal.server.common.core.security.repository.IRoleRepository;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.funcode.portal.server.common.domain.security.Role;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        authorities.add(BasicAuthority.builder().authorityName("新增或编辑权限").authorityKey("system:authority:addOrEditAuthority").build());
        authorities.add(BasicAuthority.builder().authorityName("根据不同条件模糊分页查询权限列表").authorityKey("system:authority:getAuthorityPageList").build());
        authorities.add(BasicAuthority.builder().authorityName("新增或编辑角色").authorityKey("system:role:addOrEditRole").build());
        authorities.add(BasicAuthority.builder().authorityName("根据不同条件模糊分页查询角色列表").authorityKey("system:role:getRolePageList").build());
        authorities.add(BasicAuthority.builder().authorityName("根据邮箱获取人员").authorityKey("system:user:getUserByEmail").build());
        authorities.add(BasicAuthority.builder().authorityName("根据用户名获取人员").authorityKey("system:user:getUserByUsername").build());
        authorities.add(BasicAuthority.builder().authorityName("根据微信ID获取人员").authorityKey("system:user:getUserByWechatId").build());
        authorities.add(BasicAuthority.builder().authorityName("根据不同条件模糊分页查询人员列表").authorityKey("system:user:addOrEditAuthority").build());
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
}
