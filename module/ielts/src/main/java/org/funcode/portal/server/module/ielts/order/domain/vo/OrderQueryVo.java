/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "订单查询条件VO")
public class OrderQueryVo {

    @Schema(description = "订单ID（精确）")
    private Long id;
    @Schema(description = "交易方式（0：兑换码;1：微信支付）（精确）")
    private Integer tradeType;
    @DecimalMin(value = "0.00", message = "{ielts.domain.vo.OrderQueryVo.price.DecimalMin}")
    @Schema(description = "金额范围最小值")
    private BigDecimal priceMin;
    @DecimalMin(value = "0.00", message = "{ielts.domain.vo.OrderQueryVo.price.DecimalMin}")
    @Schema(description = "金额范围最大值")
    private BigDecimal priceMax;
    @Schema(description = "付款时间的开始时间")
    private LocalDateTime paymentTimeBegin;
    @Schema(description = "付款时间的结束时间")
    private LocalDateTime paymentTimeEnd;
    @Schema(description = "创建时间的开始时间")
    private LocalDateTime createdAtBegin;
    @Schema(description = "创建时间的结束时间")
    private LocalDateTime createdAtEnd;
    @Schema(description = "更新时间的开始时间")
    private LocalDateTime updatedAtBegin;
    @Schema(description = "更新时间的结束时间")
    private LocalDateTime updatedAtEnd;
    @Schema(description = "分页参数")
    private PageRequestVo pageRequestVo;

    @Schema(hidden = true)
    public PageRequest getPageRequest() {
        return PageRequest.of(pageRequestVo.getCurrentPage() - 1, pageRequestVo.getPageSize());
    }
}
