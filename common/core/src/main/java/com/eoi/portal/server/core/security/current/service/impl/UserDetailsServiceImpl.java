/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package com.eoi.portal.server.core.security.current.service.impl;

import com.eoi.portal.server.core.security.current.domain.dto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = User.builder()
                .id(1L)
                .username("admin")
                .password("{noop}admin")
                .build();
        return user;
    }
}
