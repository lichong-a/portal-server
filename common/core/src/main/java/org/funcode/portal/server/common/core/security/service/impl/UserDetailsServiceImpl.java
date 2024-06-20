/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.security.domain.dto.User;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户唯一标识不能为空");
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
