/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.core.security.current.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
@Schema(description = "人员查询条件")
public class UserVo {
    @Size(max = 100, message = "{security.current.domain.vo.UserVo.id.Size}")
    private Long id;
    @Size(max = 100, message = "{security.current.domain.vo.UserVo.nickName.Size}")
    private String nickName;
    @Size(max = 100, message = "{security.current.domain.vo.UserVo.realName.Size}")
    private String realName;
    @Size(max = 100, message = "{security.current.domain.vo.UserVo.username.Size}")
    private String username;
    @Size(max = 100, message = "{security.current.domain.vo.UserVo.email.Size}")
    @Email(message = "{security.current.domain.vo.UserVo.email.Email}")
    private String email;
    @Size(max = 100, message = "{security.current.domain.vo.UserVo.phone.Size}")
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PageRequest pageRequest;
}
