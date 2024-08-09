/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.role.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.security.Role;
import org.funcode.portal.server.module.system.role.service.IRoleService;
import org.funcode.portal.server.module.system.role.vo.RoleAddOrEditVo;
import org.funcode.portal.server.module.system.role.vo.RoleQueryVo;
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
@Tag(name = "角色")
@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @Operation(summary = "新增或编辑角色")
    @PostMapping("addOrEditRole")
    @PreAuthorize(value = "hasAuthority('system:role:addOrEditRole')")
    public ResponseResult<Role> addOrEditRole(@Valid @RequestBody RoleAddOrEditVo roleAddOrEditVo) {
        return ResponseResult.success(roleService.addOrEditRole(roleAddOrEditVo));
    }

    @Operation(summary = "根据不同条件模糊分页查询角色列表")
    @PostMapping("pageList")
    @PreAuthorize(value = "hasAuthority('system:role:pageList')")
    public ResponseResult<Page<Role>> pageList(@Valid @RequestBody RoleQueryVo roleQueryVo) {
        return ResponseResult.success(roleService.findPage(roleQueryVo));
    }
}
