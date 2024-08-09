/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Component
@RequiredArgsConstructor
@Schema(description = "兑换码新增VO")
public class RedeemCodeAddVo {

    @Schema(description = "过期时间（默认1天过期）")
    private LocalDateTime expireTime;
    @Schema(description = "课程ID列表")
    private List<Long> courseIds;
    @Schema(description = "课程专栏ID列表")
    private List<Long> courseColumnIds;

}
