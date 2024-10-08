/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.funcode.portal.server.common.domain.ielts.Order;
import org.funcode.portal.server.module.ielts.order.domain.vo.OrderQueryVo;
import org.funcode.portal.server.module.ielts.order.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @Operation(summary = "根据不同条件分页查询订单列表")
    @PostMapping("/pageList")
    @PreAuthorize("hasAuthority('ielts:order:pageList')")
    public ResponseResult<Page<Order>> pageList(@Valid @RequestBody OrderQueryVo orderQueryVo) {
        return ResponseResult.success(orderService.pageList(orderQueryVo));
    }

    @Operation(summary = "分页查询当前登录人的订单列表")
    @GetMapping("/pageList/currentUser")
    public ResponseResult<Page<Order>> pageList(@Valid PageRequestVo pageRequestVo) {
        return ResponseResult.success(orderService.pageListCurrentUser(pageRequestVo));
    }
}
