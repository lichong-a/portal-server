/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.authority.domain.vo;

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
@Schema(description = "权限查询条件VO")
public class AuthorityQueryVo {
    @Size(max = 100, message = "{sysyem.domain.vo.AuthorityQueryVo.id.Size}")
    private Long id;
    @Size(max = 100, message = "{sysyem.domain.vo.AuthorityQueryVo.authorityName.Size}")
    private String authorityName;
    @Size(max = 100, message = "{sysyem.domain.vo.AuthorityQueryVo.authorityKey.Size}")
    private String authorityKey;
    @Size(max = 500, message = "{sysyem.domain.vo.AuthorityQueryVo.description.Size}")
    private String description;
    private LocalDateTime createdAtBegin;
    private LocalDateTime createdAtEnd;
    private LocalDateTime updatedAtBegin;
    private LocalDateTime updatedAtEnd;
    private PageRequest pageRequest;
}
