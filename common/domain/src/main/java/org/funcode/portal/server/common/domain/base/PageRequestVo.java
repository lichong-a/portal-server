/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "分页参数")
public class PageRequestVo {

    @Schema(description = "当前页码")
    private Integer currentPage;

    @Schema(description = "每页数量")
    private Integer pageSize;
}
