/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Component
@RequiredArgsConstructor
@Schema(description = "兑换码新增或编辑VO")
public class RedeemCodeAddOrEditVo {

    @Schema(description = "兑换码ID")
    private Long id;
    @Size(max = 100, message = "{ielts.domain.vo.RedeemCodeAddOrEditVo.title.Size}")
    @Schema(description = "兑换码标题")
    private String title;

}
