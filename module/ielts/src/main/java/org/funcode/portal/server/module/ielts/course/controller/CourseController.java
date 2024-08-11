/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.module.ielts.course.domain.vo.CourseAddOrEditVo;
import org.funcode.portal.server.module.ielts.course.domain.vo.CourseQueryVo;
import org.funcode.portal.server.module.ielts.course.service.ICourseService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "课程管理")
@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @Operation(summary = "新增或编辑课程")
    @PostMapping("addOrEdit")
    @PreAuthorize(value = "hasAuthority('ielts:course:addOrEdit')")
    public ResponseResult<Course> addOrEdit(CourseAddOrEditVo courseAddOrEditVo) {
        return ResponseResult.success(courseService.addOrEdit(courseAddOrEditVo));
    }

    @Operation(summary = "根据ID删除课程")
    @GetMapping("delete")
    @PreAuthorize(value = "hasAuthority('ielts:course:delete')")
    public ResponseResult<Void> delete(@RequestParam Long id) {
        courseService.delete(id);
        return ResponseResult.success();
    }

    @Operation(summary = "根据ID查询课程")
    @GetMapping("findById")
    @PreAuthorize(value = "hasAuthority('ielts:course:findById')")
    public ResponseResult<Course> findById(@RequestParam Long id) {
        return ResponseResult.success(courseService.find(id));
    }

    @Operation(summary = "根据不同条件分页查询课程列表")
    @PostMapping("pageList")
    @PreAuthorize(value = "hasAuthority('ielts:course:pageList')")
    public ResponseResult<Page<Course>> pageList(@Valid @RequestBody CourseQueryVo courseQueryVo) {
        return ResponseResult.success(courseService.findPage(courseQueryVo));
    }
}
