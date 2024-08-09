/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.module.ielts.storage.domain.vo.StorageAddOrEditVo;
import org.funcode.portal.server.module.ielts.storage.domain.vo.StorageQueryVo;
import org.funcode.portal.server.module.ielts.storage.service.IStorageService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "存储管理")
@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final IStorageService storageService;

    @Operation(summary = "上传文件")
    @PostMapping("upload")
    @PreAuthorize(value = "hasAuthority('ielts:storage:upload')")
    public ResponseResult<Boolean> upload(@Valid @RequestBody StorageAddOrEditVo storageAddOrEditVo) {
        return ResponseResult.success(storageService.upload(storageAddOrEditVo));
    }

    @Operation(summary = "删除文件")
    @GetMapping("delete")
    @PreAuthorize(value = "hasAuthority('ielts:storage:delete')")
    public ResponseResult<Boolean> delete(@RequestParam @Parameter(description = "存储ID") Long storageId) {
        return ResponseResult.success(storageService.deleteStorage(storageId));
    }

    @Operation(summary = "根据不同条件模糊分页查询存储列表")
    @PostMapping("pageList")
    @PreAuthorize(value = "hasAuthority('ielts:storage:pageList')")
    public ResponseResult<Page<Storage>> getAuthorityPageList(@Valid @RequestBody StorageQueryVo storageQueryVo) {
        return ResponseResult.success(storageService.findPage(storageQueryVo));
    }

}
