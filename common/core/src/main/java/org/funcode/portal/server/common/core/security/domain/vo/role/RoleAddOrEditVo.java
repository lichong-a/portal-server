/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Component
@RequiredArgsConstructor
@Schema(description = "角色新增或编辑VO")
public class RoleAddOrEditVo {

    @Schema(description = "角色ID")
    private Long id;
    @Size(max = 100, message = "{system.domain.vo.RoleEditVo.roleName.Size}")
    @Schema(description = "角色名称")
    private String roleName;
    @Size(max = 100, message = "{system.domain.vo.RoleEditVo.roleKey.Size}")
    @Schema(description = "角色标识")
    private String roleKey;
    @Size(max = 500, message = "{system.domain.vo.RoleEditVo.description.Size}")
    @Schema(description = "角色描述")
    private String description;
    @Schema(description = "权限ID列表")
    private List<Long> authorityIds;

}
