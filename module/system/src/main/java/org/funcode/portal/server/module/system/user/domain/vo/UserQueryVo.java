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
    @Schema(description = "人员ID（精确）")
    private Long id;
    @Size(max = 100, message = "{system.domain.vo.UserQueryVo.nickName.Size}")
    @Schema(description = "昵称（模糊）")
    private String nickName;
    @Size(max = 100, message = "{system.domain.vo.UserQueryVo.realName.Size}")
    @Schema(description = "真实姓名（模糊）")
    private String realName;
    @Size(max = 100, message = "{system.domain.vo.UserQueryVo.username.Size}")
    @Schema(description = "用户名（模糊）")
    private String username;
    @Size(max = 100, message = "{system.domain.vo.UserQueryVo.email.Size}")
    @Email(message = "{system.domain.vo.UserQueryVo.email.Email}")
    @Schema(description = "邮箱（模糊）")
    private String email;
    @Size(max = 100, message = "{system.domain.vo.UserQueryVo.phone.Size}")
    @Schema(description = "手机号（模糊）")
    private String phone;
    @Size(max = 100, message = "{system.domain.vo.UserQueryVo.wechatId.Size}")
    @Schema(description = "微信ID（模糊）")
    private String wechatId;
    @Schema(description = "创建时间的开始时间（）")
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
