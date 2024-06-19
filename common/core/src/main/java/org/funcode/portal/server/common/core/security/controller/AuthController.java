/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.security.domain.vo.request.SignInRequest;
import org.funcode.portal.server.common.core.security.domain.vo.request.SignUpRequest;
import org.funcode.portal.server.common.core.security.service.IAuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "权限")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/signup")
    @Operation(summary = "注册")
    public ResponseResult<String> signup(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    @Operation(summary = "登录")
    public ResponseResult<String> signin(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signin(request);
    }
}
