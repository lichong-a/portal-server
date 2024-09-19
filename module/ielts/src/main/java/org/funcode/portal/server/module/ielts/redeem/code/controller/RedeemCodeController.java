/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.ielts.RedeemCode;
import org.funcode.portal.server.module.ielts.redeem.code.domain.vo.RedeemCodeAddVo;
import org.funcode.portal.server.module.ielts.redeem.code.domain.vo.RedeemCodeQueryVo;
import org.funcode.portal.server.module.ielts.redeem.code.service.IRedeemCodeService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "兑换码管理")
@RestController
@RequestMapping("/api/v1/redeem-code")
@RequiredArgsConstructor
public class RedeemCodeController {

    private final IRedeemCodeService redeemCodeService;

    @Operation(summary = "兑换码兑换")
    @GetMapping("/redeem")
    @PreAuthorize("hasAuthority('ielts:redeemCode:redeem')")
    public ResponseResult<RedeemCode> redeem(String code) {
        return ResponseResult.success(redeemCodeService.redeem(code));
    }

    @Operation(summary = "新增兑换码")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ielts:redeemCode:add')")
    public ResponseResult<RedeemCode> add(@Valid @RequestBody RedeemCodeAddVo redeemCodeAddVo) {
        return ResponseResult.success(redeemCodeService.addRedeemCode(redeemCodeAddVo));
    }

    @Operation(summary = "使兑换码失效")
    @GetMapping("/invalid")
    @PreAuthorize("hasAuthority('ielts:redeemCode:invalid')")
    public ResponseResult<RedeemCode> invalid(long redeemCodeId) {
        return ResponseResult.success(redeemCodeService.invalidRedeemCode(redeemCodeId));
    }

    @Operation(summary = "兑换码删除")
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ielts:redeemCode:delete')")
    public ResponseResult<Boolean> delete(String code) {
        return ResponseResult.success(redeemCodeService.deleteRedeemCode(code));
    }

    @Operation(summary = "根据不同条件分页查询兑换码列表")
    @PostMapping("/pageList")
    @PreAuthorize("hasAuthority('ielts:redeemCode:pageList')")
    public ResponseResult<Page<RedeemCode>> pageList(@Valid @RequestBody RedeemCodeQueryVo redeemCodeQueryVo) {
        return ResponseResult.success(redeemCodeService.pageList(redeemCodeQueryVo));
    }
}
