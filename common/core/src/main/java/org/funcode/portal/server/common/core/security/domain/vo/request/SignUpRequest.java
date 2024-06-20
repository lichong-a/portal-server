/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
public class SignUpRequest {

    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.nickName.Size}")
    private String nickName;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.realName.Size}")
    private String realName;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.username.Size}")
    @IsUsernameValid(message = "{security.current.domain.vo.request.SignUpRequest.username.IsUsernameValid}")
    private String username;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.gender.Size}")
    private String gender;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.email.Size}")
    @Email(message = "{security.current.domain.vo.request.SignUpRequest.email.Email}")
    private String email;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.phone.Size}")
    @IsPhone(message = "{security.current.domain.vo.request.SignUpRequest.phone.IsPhone}")
    private String phone;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.password.Size}")
    private String password;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignUpRequest.avatar.Size}")
    private String avatar;
    private LocalDate birthday;
}
