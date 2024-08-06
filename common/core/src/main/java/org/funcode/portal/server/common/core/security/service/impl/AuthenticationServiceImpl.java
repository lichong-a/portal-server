/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.base.http.response.ResponseStatusEnum;
import org.funcode.portal.server.common.core.security.domain.vo.request.SignUpRequest;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.funcode.portal.server.common.core.security.service.IAuthenticationService;
import org.funcode.portal.server.common.core.security.service.IJwtService;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseResult<String> signup(SignUpRequest request) {
        // 判断用户是否已经存在
        var hasExistsUser = userRepository.exists((Specification<User>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.or(
                        StringUtils.isNotBlank(request.getEmail()) ? criteriaBuilder.equal(root.get("email"), request.getEmail()) : null,
                        StringUtils.isNotBlank(request.getUsername()) ? criteriaBuilder.equal(root.get("username"), request.getUsername()) : null,
                        StringUtils.isNotBlank(request.getPhone()) ? criteriaBuilder.equal(root.get("phone"), request.getPhone()) : null
                )
        ).getRestriction());
        if (hasExistsUser) {
            var hasExistsEmail = userRepository.exists((Specification<User>) (root, query, criteriaBuilder) ->
                    query.where(criteriaBuilder.equal(root.get("email"), request.getEmail())).getRestriction());
            if (hasExistsEmail) {
                return ResponseResult.fail("邮箱已被注册，请使用邮箱直接登录", ResponseStatusEnum.HTTP_STATUS_400);
            }
            var hasExistsUsername = userRepository.exists((Specification<User>) (root, query, criteriaBuilder) ->
                    query.where(criteriaBuilder.equal(root.get("username"), request.getUsername())).getRestriction());
            if (hasExistsUsername) {
                return ResponseResult.fail("用户名已被注册，请使用用户名直接登录", ResponseStatusEnum.HTTP_STATUS_400);
            }
            var hasExistsPhone = userRepository.exists((Specification<User>) (root, query, criteriaBuilder) ->
                    query.where(criteriaBuilder.equal(root.get("phone"), request.getPhone())).getRestriction());
            if (hasExistsPhone) {
                return ResponseResult.fail("手机号已被注册，请使用手机号直接登录", ResponseStatusEnum.HTTP_STATUS_400);
            }
        }
        // 创建用户
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .avatar(request.getAvatar())
                .gender(request.getGender())
                .nickName(request.getNickName())
                .realName(request.getRealName())
                .birthday(request.getBirthday())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        return ResponseResult.success();
    }

}
