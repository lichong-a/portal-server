/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.column.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.ielts.CourseColumn;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnAddOrEditVo;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnQueryVo;
import org.funcode.portal.server.module.ielts.column.service.ICourseColumnService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "专栏管理")
@RestController
@RequestMapping("/api/v1/course-column")
@RequiredArgsConstructor
public class CourseColumnController {

    private final ICourseColumnService courseColumnService;

    @Operation(summary = "新增或编辑专栏")
    @PostMapping("addOrEdit")
    @PreAuthorize(value = "hasAuthority('ielts:courseColumn:addOrEdit')")
    public ResponseResult<CourseColumn> addOrEdit(CourseColumnAddOrEditVo courseColumnAddOrEditVo) {
        return ResponseResult.success(courseColumnService.addOrEdit(courseColumnAddOrEditVo));
    }

    @Operation(summary = "根据ID删除专栏")
    @GetMapping("delete")
    @PreAuthorize(value = "hasAuthority('ielts:courseColumn:delete')")
    public ResponseResult<Void> delete(@RequestParam Long id) {
        courseColumnService.delete(id);
        return ResponseResult.success();
    }

    @Operation(summary = "根据ID查询专栏")
    @GetMapping("findById")
    @PreAuthorize(value = "hasAuthority('ielts:courseColumn:findById')")
    public ResponseResult<CourseColumn> find(@RequestParam Long id) {
        return ResponseResult.success(courseColumnService.find(id));
    }

    @Operation(summary = "根据不同条件分页查询专栏列表")
    @PostMapping("pageList")
    @PreAuthorize(value = "hasAuthority('ielts:courseColumn:pageList')")
    public ResponseResult<Page<CourseColumn>> pageList(@Valid @RequestBody CourseColumnQueryVo courseColumnQueryVo) {
        return ResponseResult.success(courseColumnService.findPage(courseColumnQueryVo));
    }
}
