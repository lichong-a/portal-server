/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.core.security.current.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.core.base.http.response.ResponseResult;
import org.funcode.portal.server.core.base.http.response.ResponseStatusEnum;
import org.funcode.portal.server.core.security.current.domain.dto.User;
import org.funcode.portal.server.core.security.current.domain.vo.request.SignInRequest;
import org.funcode.portal.server.core.security.current.domain.vo.request.SignUpRequest;
import org.funcode.portal.server.core.security.current.repository.IUserRepository;
import org.funcode.portal.server.core.security.current.service.IAuthenticationService;
import org.funcode.portal.server.core.security.current.service.IJwtService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
        var jwt = jwtService.generateToken(user);
        return ResponseResult.success(jwt);
    }

    @Override
    public ResponseResult<String> signin(SignInRequest request) {
        if (StringUtils.isBlank(request.getUsername()) && StringUtils.isBlank(request.getEmail()) && StringUtils.isBlank(request.getPhone())) {
            return ResponseResult.fail("用户名、邮箱、手机号不能同时为空", ResponseStatusEnum.HTTP_STATUS_400);
        }
        if (StringUtils.isBlank(request.getPassword())) {
            return ResponseResult.fail("密码不能为空", ResponseStatusEnum.HTTP_STATUS_400);
        }
        User user = null;
        if (StringUtils.isNotBlank(request.getUsername())) {
            user = userRepository.findByUsername(request.getUsername());
        } else if (StringUtils.isNotBlank(request.getEmail())) {
            user = userRepository.findByEmail(request.getEmail());
        } else if (StringUtils.isNotBlank(request.getPhone())) {
            user = userRepository.findByPhone(request.getPhone());
        }
        if (user == null) {
            return ResponseResult.fail("用户名或密码不正确", ResponseStatusEnum.HTTP_STATUS_401);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
        var jwt = jwtService.generateToken(user);
        return ResponseResult.success(jwt);
    }
}
