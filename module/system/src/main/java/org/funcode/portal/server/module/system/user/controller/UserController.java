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
import org.funcode.portal.server.common.domain.security.User;
import org.funcode.portal.server.module.system.user.domain.vo.UserQueryVo;
import org.funcode.portal.server.module.system.user.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize(value = "hasAuthority('system:user:getUserByEmail')")
    public ResponseResult<User> getUserByEmail(@RequestParam String email) {
        return ResponseResult.success(userService.findByEmail(email));
    }

    @Operation(summary = "根据用户名获取人员")
    @GetMapping("getUserByUsername")
    @PreAuthorize(value = "hasAuthority('system:user:getUserByUsername')")
    public ResponseResult<User> getUserByUsername(@RequestParam String username) {
        return ResponseResult.success(userService.findByUsername(username));
    }

    @Operation(summary = "根据微信ID获取人员")
    @GetMapping("getUserByWechatId")
    @PreAuthorize(value = "hasAuthority('system:user:getUserByWechatId')")
    public ResponseResult<User> getUserByWechatId(@RequestParam String wechatId) {
        return ResponseResult.success(userService.findByWechatId(wechatId));
    }

    @Operation(summary = "根据不同条件模糊分页查询人员列表")
    @PostMapping("pageList")
    @PreAuthorize(value = "hasAuthority('system:user:pageList')")
    public ResponseResult<Page<User>> pageList(@Valid @RequestBody UserQueryVo userQueryVo) {
        return ResponseResult.success(userService.findPage(userQueryVo));
    }
}
