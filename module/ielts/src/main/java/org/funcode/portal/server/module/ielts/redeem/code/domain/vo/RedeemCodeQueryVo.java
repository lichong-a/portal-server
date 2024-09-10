/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "兑换码查询条件VO")
public class RedeemCodeQueryVo {

    @Schema(description = "兑换码ID（精确）")
    private Long id;
    @Schema(description = "状态（0：待消费;1：已消费；2:手动弃用）（精确）")
    private Integer status;
    @Size(max = 200, message = "{ielts.domain.vo.RedeemCodeQueryVo.code.Size}")
    @Schema(description = "兑换码code（模糊）")
    private String code;
    @Schema(description = "过期时间的开始时间")
    private LocalDateTime expireTimeBegin;
    @Schema(description = "过期时间的结束时间")
    private LocalDateTime expireTimeEnd;
    @Schema(description = "兑换时间的开始时间")
    private LocalDateTime redeemTimeBegin;
    @Schema(description = "兑换时间的结束时间")
    private LocalDateTime redeemTimeEnd;
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
        return PageRequest.of(pageRequestVo.getCurrent() - 1, pageRequestVo.getPageSize());
    }
}
