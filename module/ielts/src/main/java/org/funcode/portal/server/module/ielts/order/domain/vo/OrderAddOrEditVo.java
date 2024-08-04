/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Component
@RequiredArgsConstructor
@Schema(description = "订单新增或编辑VO")
public class OrderAddOrEditVo {

    @Schema(description = "订单ID")
    private Long id;

    @NotNull(message = "{ielts.domain.vo.OrderAddOrEditVo.order.NotNull}")
    @Schema(description = "交易方式（0：兑换码;1：微信支付）")
    private long order;

    @Schema(description = "付款时间")
    private LocalDateTime paymentTime;

    @NotNull(message = "{ielts.domain.vo.OrderAddOrEditVo.price.NotNull}")
    @DecimalMin(value = "0.00", message = "{ielts.domain.vo.OrderAddOrEditVo.price.DecimalMin}")
    @Schema(description = "金额")
    private BigDecimal price;

}
