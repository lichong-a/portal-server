/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.security.domain.dto.User;
import org.funcode.portal.server.module.system.user.domain.vo.UserVo;
import org.funcode.portal.server.module.system.user.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "人员")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @Operation(summary = "根据邮箱获取人员")
    @GetMapping("getUserByEmail")
    public ResponseResult<User> getUserByEmail(@RequestParam String email) {
        return ResponseResult.success(userService.findByEmail(email));
    }

    @Operation(summary = "根据不同条件获取人员列表")
    @PostMapping("getUserPageList")
    public ResponseResult<Page<User>> getUserPageList(@Valid @RequestBody UserVo userVo) {
        return ResponseResult.success(userService.findPage(userVo));
    }
}
