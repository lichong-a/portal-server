/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.column.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Component
@RequiredArgsConstructor
@Schema(description = "专栏新增或编辑VO")
public class CourseColumnAddOrEditVo {

    @Schema(description = "专栏ID")
    private Long id;
    @NotNull(message = "{ielts.domain.vo.CourseColumnAddOrEditVo.title.NotNull}")
    @Size(max = 100, message = "{ielts.domain.vo.CourseColumnAddOrEditVo.title.Size}")
    @Schema(description = "专栏标题")
    private String title;
    @NotNull(message = "{ielts.domain.vo.CourseColumnAddOrEditVo.status.NotNull}")
    @Schema(description = "专栏状态（0：已下架;1：已上架;2：下架并静止播放）")
    private Integer status;
    @NotNull(message = "{ielts.domain.vo.CourseColumnAddOrEditVo.price.NotNull}")
    @DecimalMin(value = "0.00", message = "{ielts.domain.vo.CourseColumnAddOrEditVo.price.DecimalMin}")
    @Schema(description = "价格")
    private BigDecimal price;
    @Schema(description = "专栏封面文件ID")
    private Long courseColumnCoverStorageId;
    @Schema(description = "专栏简介文件ID")
    private Long courseColumnDescriptionStorageId;
    @Schema(description = "课程ID列表")
    private List<Long> courseIds;

}
