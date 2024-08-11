/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.vo.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.funcode.portal.server.common.core.base.validator.annotation.IsPhone;
import org.funcode.portal.server.common.core.base.validator.annotation.IsUsernameValid;

import java.time.LocalDate;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Schema(name = "注册请求VO")
public class SignUpRequest {

    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.nickName.Size}")
    @Schema(description = "昵称")
    private String nickName;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.realName.Size}")
    @Schema(description = "真实姓名")
    private String realName;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.username.Size}")
    @IsUsernameValid(message = "{core.security.current.domain.vo.signup.SignUpRequest.username.IsUsernameValid}")
    @Schema(description = "用户名")
    private String username;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.gender.Size}")
    @Schema(description = "性别")
    private String gender;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.email.Size}")
    @Email(message = "{core.security.current.domain.vo.signup.SignUpRequest.email.Email}")
    @Schema(description = "邮箱")
    private String email;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.phone.Size}")
    @IsPhone(message = "{core.security.current.domain.vo.signup.SignUpRequest.phone.IsPhone}")
    @Schema(description = "手机号")
    private String phone;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.password.Size}")
    @Schema(description = "密码")
    private String password;
    @Size(max = 100, message = "{core.security.current.domain.vo.signup.SignUpRequest.avatar.Size}")
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "生日")
    private LocalDate birthday;
}
