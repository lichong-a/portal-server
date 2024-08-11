/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "角色查询条件VO")
public class RoleQueryVo {
    @Schema(description = "角色ID（精确）")
    private Long id;
    @Size(max = 100, message = "{system.domain.vo.AuthorityQueryVo.authorityName.Size}")
    @Schema(description = "角色名称（模糊）")
    private String roleName;
    @Size(max = 100, message = "{system.domain.vo.AuthorityQueryVo.authorityKey.Size}")
    @Schema(description = "角色标识（模糊）")
    private String roleKey;
    @Size(max = 500, message = "{system.domain.vo.AuthorityQueryVo.description.Size}")
    @Schema(description = "角色描述（模糊）")
    private String description;
    @Schema(description = "创建时间的开始时间")
    private LocalDateTime createdAtBegin;
    @Schema(description = "创建时间的结束时间")
    private LocalDateTime createdAtEnd;
    @Schema(description = "更新时间的开始时间")
    private LocalDateTime updatedAtBegin;
    @Schema(description = "更新时间的结束时间")
    private LocalDateTime updatedAtEnd;
    @Schema(description = "分页参数")
    private PageRequest pageRequest;
}
