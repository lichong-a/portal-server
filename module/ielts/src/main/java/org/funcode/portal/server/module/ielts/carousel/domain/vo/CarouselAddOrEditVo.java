/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.carousel.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "轮播新增或编辑VO")
public class CarouselAddOrEditVo {

    @Schema(description = "轮播ID")
    private Long id;
    @Size(max = 100, message = "{ielts.domain.vo.CarouselAddOrEditVo.title.Size}")
    @Schema(description = "轮播标题")
    private String title;
    @Schema(description = "顺序")
    private long carouselOrder;
    @NotNull(message = "{ielts.domain.vo.CarouselAddOrEditVo.storageId.NotNull}")
    @Schema(description = "轮播图片文件ID")
    private Long storageId;

}
