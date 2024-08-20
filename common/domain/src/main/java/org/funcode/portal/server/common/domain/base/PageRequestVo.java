/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "分页参数")
public class PageRequestVo {

    @Min(value = 1, message = "{base.domain.PageRequestVo.currentPage.Min}")
    @Schema(description = "当前页码")
    private Integer currentPage;

    @Min(value = 1, message = "{base.domain.PageRequestVo.pageSize.Min}")
    @Schema(description = "每页数量")
    private Integer pageSize;

    @Schema(hidden = true)
    public PageRequest getPageRequest() {
        return PageRequest.of(currentPage - 1, pageSize);
    }
}
