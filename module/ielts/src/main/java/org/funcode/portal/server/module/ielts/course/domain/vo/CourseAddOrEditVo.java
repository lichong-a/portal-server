/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.domain.vo;

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
@Schema(description = "课程新增或编辑VO")
public class CourseAddOrEditVo {

    @Schema(description = "课程ID")
    private Long id;
    @NotNull(message = "{ielts.domain.vo.CourseAddOrEditVo.title.NotNull}")
    @Size(max = 100, message = "{ielts.domain.vo.CourseAddOrEditVo.title.Size}")
    @Schema(description = "课程标题")
    private String title;
    @NotNull(message = "{ielts.domain.vo.CourseAddOrEditVo.status.NotNull}")
    @Schema(description = "课程状态（0：已下架;1：已上架;2：下架并静止播放）")
    private int status;
    @DecimalMin(value = "0.00", message = "{ielts.domain.vo.CourseAddOrEditVo.price.DecimalMin}")
    @Schema(description = "课程价格")
    private BigDecimal price;
    @Schema(description = "课程封面文件ID")
    private Long courseCoverStorageId;
    @NotNull(message = "{ielts.domain.vo.CourseAddOrEditVo.courseMediaStorageId.NotNull}")
    @Schema(description = "课程音视频文件ID")
    private Long courseMediaStorageId;
    @Schema(description = "课程简介文件ID")
    private Long courseDescriptionStorageId;
    @Schema(description = "课程附件ID列表")
    private List<Long> courseAttachmentStorageIds;

}
