/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.domain.vo;

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
@Schema(description = "课程查询条件VO")
public class CourseQueryVo {

    @Schema(description = "课程ID（精确）")
    private Long id;
    @Size(max = 100, message = "{ielts.domain.vo.CourseQueryVo.title.Size}")
    @Schema(description = "课程标题（模糊）")
    private String title;
    @Schema(description = "课程状态（0：已下架;1：已上架;2：下架并静止播放）（精确）")
    private Integer status;
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
