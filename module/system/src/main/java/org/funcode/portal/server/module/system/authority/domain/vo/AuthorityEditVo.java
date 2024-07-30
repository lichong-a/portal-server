/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.authority.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.funcode.portal.server.common.core.security.domain.dto.BasicAuthority;
import org.springframework.beans.BeanUtils;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "权限编辑VO")
public class AuthorityEditVo {

    @NotNull(message = "{sysyem.domain.vo.AuthorityEditVo.id.NotNull}")
    private Long id;
    @Size(max = 100, message = "{sysyem.domain.vo.AuthorityEditVo.authorityName.Size}")
    private String authorityName;
    @Size(max = 100, message = "{sysyem.domain.vo.AuthorityEditVo.authorityKey.Size}")
    private String authorityKey;
    @Size(max = 500, message = "{sysyem.domain.vo.AuthorityEditVo.description.Size}")
    private String description;

    public BasicAuthority transToBasicAuthority() {
        BasicAuthority basicAuthority = new BasicAuthority();
        BeanUtils.copyProperties(this, basicAuthority);
        return basicAuthority;
    }
}
