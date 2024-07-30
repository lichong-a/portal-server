/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.role.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.funcode.portal.server.common.core.security.domain.dto.Role;
import org.springframework.beans.BeanUtils;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "角色编辑VO")
public class RoleEditVo {

    @NotNull(message = "{sysyem.domain.vo.RoleEditVo.id.NotNull}")
    private Long id;
    @Size(max = 100, message = "{sysyem.domain.vo.RoleEditVo.roleName.Size}")
    private String roleName;
    @Size(max = 100, message = "{sysyem.domain.vo.RoleEditVo.roleKey.Size}")
    private String roleKey;
    @Size(max = 500, message = "{sysyem.domain.vo.RoleEditVo.description.Size}")
    private String description;

    public Role transToRole() {
        Role role = new Role();
        BeanUtils.copyProperties(this, role);
        return role;
    }
}
