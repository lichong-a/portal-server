/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.authority.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.security.domain.dto.BasicAuthority;
import org.funcode.portal.server.module.system.authority.domain.vo.AuthorityAddVo;
import org.funcode.portal.server.module.system.authority.domain.vo.AuthorityEditVo;
import org.funcode.portal.server.module.system.authority.domain.vo.AuthorityQueryVo;
import org.funcode.portal.server.module.system.authority.service.IAuthorityService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final IAuthorityService authorityService;

    @Operation(summary = "新增权限")
    @PostMapping("addAuthority")
    @PreAuthorize(value = "hasAuthority('system:authority:addAuthority')")
    public ResponseResult<Boolean> addAuthority(@Valid @RequestBody AuthorityAddVo authorityAddVo) {
        return ResponseResult.success();
    }

    @Operation(summary = "编辑权限")
    @PostMapping("editAuthority")
    @PreAuthorize(value = "hasAuthority('system:authority:editAuthority')")
    public ResponseResult<Boolean> editAuthority(@Valid @RequestBody AuthorityEditVo authorityEditVo) {
        return ResponseResult.success();
    }

    @Operation(summary = "根据不同条件模糊分页查询权限列表")
    @PostMapping("getAuthorityPageList")
    @PreAuthorize(value = "hasAuthority('system:authority:getAuthorityPageList')")
    public ResponseResult<Page<BasicAuthority>> getAuthorityPageList(@Valid @RequestBody AuthorityQueryVo authorityQueryVo) {
        return ResponseResult.success(authorityService.findPage(authorityQueryVo));
    }
}
