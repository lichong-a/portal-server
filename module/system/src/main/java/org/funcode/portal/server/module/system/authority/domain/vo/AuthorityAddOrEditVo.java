/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.authority.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.springframework.beans.BeanUtils;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "权限新增或编辑VO")
public class AuthorityAddOrEditVo {

    @Schema(description = "权限ID")
    private Long id;
    @Size(max = 100, message = "{system.domain.vo.AuthorityEditVo.authorityName.Size}")
    @Schema(description = "权限名称")
    private String authorityName;
    @Size(max = 100, message = "{system.domain.vo.AuthorityEditVo.authorityKey.Size}")
    @Schema(description = "权限标识")
    private String authorityKey;
    @Size(max = 500, message = "{system.domain.vo.AuthorityEditVo.description.Size}")
    @Schema(description = "权限描述")
    private String description;

    public BasicAuthority transToBasicAuthority() {
        BasicAuthority basicAuthority = new BasicAuthority();
        BeanUtils.copyProperties(this, basicAuthority);
        return basicAuthority;
    }
}
