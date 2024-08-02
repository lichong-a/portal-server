/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.config.ApplicationConfig;
import org.funcode.portal.server.common.core.security.domain.dto.User;
import org.funcode.portal.server.common.core.security.repository.IBasicAuthorityRepository;
import org.funcode.portal.server.common.core.security.repository.IRoleRepository;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IBasicAuthorityRepository basicAuthorityRepository;
    private final ApplicationConfig applicationConfig;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository,
                                  IRoleRepository roleRepository,
                                  IBasicAuthorityRepository basicAuthorityRepository,
                                  ApplicationConfig applicationConfig,
                                  // 为解决依赖循环，此处需要使用@Lazy
                                  @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.basicAuthorityRepository = basicAuthorityRepository;
        this.applicationConfig = applicationConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户唯一标识不能为空");
        }
        String adminUsername = applicationConfig.getSecurity().adminUsername();
        // 指定唯一的后台管理员
        if (adminUsername.equals(username)) {
            return User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(applicationConfig.getSecurity().adminPassword()))
                    .realName("管理员")
                    .nickName("管理员")
                    .roles(new HashSet<>(roleRepository.findAll()))
                    .basicAuthorities(new HashSet<>(basicAuthorityRepository.findAll()))
                    .build();
        }
        Optional<User> user = userRepository.findOne(
                (Specification<User>) (root, query, cb) -> query.where(cb.or(
                        cb.equal(root.get("username"), username),
                        cb.equal(root.get("phone"), username),
                        cb.equal(root.get("email"), username)
                )).getRestriction());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user.get();
    }
}
