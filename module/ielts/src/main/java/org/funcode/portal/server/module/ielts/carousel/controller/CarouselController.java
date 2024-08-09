/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.carousel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.ielts.Carousel;
import org.funcode.portal.server.module.ielts.carousel.domain.vo.CarouselAddOrEditVo;
import org.funcode.portal.server.module.ielts.carousel.domain.vo.CarouselQueryVo;
import org.funcode.portal.server.module.ielts.carousel.service.ICarouselService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "轮播管理")
@RestController
@RequestMapping("/api/v1/carousel")
@RequiredArgsConstructor
public class CarouselController {

    private final ICarouselService carouselService;

    @Operation(summary = "新增或编辑轮播")
    @PostMapping("addOrEdit")
    @PreAuthorize(value = "hasAuthority('ielts:carousel:addOrEdit')")
    public ResponseResult<Carousel> addOrEdit(@Valid @RequestBody CarouselAddOrEditVo carouselAddOrEditVo) {
        return ResponseResult.success(carouselService.addOrEdit(carouselAddOrEditVo));
    }

    @Operation(summary = "根据不同条件分页查询轮播列表")
    @PostMapping("pageList")
    @PreAuthorize(value = "hasAuthority('ielts:carousel:pageList')")
    public ResponseResult<Page<Carousel>> pageList(@Valid @RequestBody CarouselQueryVo carouselQueryVo) {
        return ResponseResult.success(carouselService.findPage(carouselQueryVo));
    }

    @Operation(summary = "根据ID删除轮播")
    @GetMapping("delete")
    @PreAuthorize(value = "hasAuthority('ielts:carousel:delete')")
    public ResponseResult<Void> delete(@RequestParam Long id) {
        carouselService.delete(id);
        return ResponseResult.success();
    }

    @Operation(summary = "获取当前所有可用的轮播列表")
    @GetMapping("availableList")
    public ResponseResult<List<Carousel>> availableList() {
        return ResponseResult.success(carouselService.findAll());
    }
}
