/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "人员查询条件VO")
public class UserQueryVo {
    @Size(max = 100, message = "{sysyem.domain.vo.UserQueryVo.id.Size}")
    private Long id;
    @Size(max = 100, message = "{sysyem.domain.vo.UserQueryVo.nickName.Size}")
    private String nickName;
    @Size(max = 100, message = "{sysyem.domain.vo.UserQueryVo.realName.Size}")
    private String realName;
    @Size(max = 100, message = "{sysyem.domain.vo.UserQueryVo.username.Size}")
    private String username;
    @Size(max = 100, message = "{sysyem.domain.vo.UserQueryVo.email.Size}")
    @Email(message = "{sysyem.domain.vo.UserQueryVo.email.Email}")
    private String email;
    @Size(max = 100, message = "{sysyem.domain.vo.UserQueryVo.phone.Size}")
    private String phone;
    private LocalDateTime createdAtBegin;
    private LocalDateTime createdAtEnd;
    private LocalDateTime updatedAtBegin;
    private LocalDateTime updatedAtEnd;
    private PageRequest pageRequest;
}
