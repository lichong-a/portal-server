/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package com.eoi.portal.server.core.business.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
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
    @Max(value = 100, message = "{business.user.domain.vo.UserVo.id.Max}")
    private Long id;
    @Max(value = 100, message = "{business.user.domain.vo.UserVo.nickName.Max}")
    private String nickName;
    @Max(value = 100, message = "{business.user.domain.vo.UserVo.realName.Max}")
    private String realName;
    @Max(value = 100, message = "{business.user.domain.vo.UserVo.username.Max}")
    private String username;
    @Max(value = 100, message = "{business.user.domain.vo.UserVo.email.Max}")
    @Email(message = "{business.user.domain.vo.UserVo.email.Email}")
    private String email;
    @Max(value = 100, message = "{business.user.domain.vo.UserVo.phone.Max}")
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PageRequest pageRequest;
}
