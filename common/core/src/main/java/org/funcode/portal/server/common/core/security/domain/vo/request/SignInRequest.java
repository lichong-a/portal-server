/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.domain.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class SignInRequest {

    @Size(max = 100, message = "{security.current.domain.vo.request.SignInRequest.username.Size}")
    private String username;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignInRequest.email.Size}")
    private String email;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignInRequest.phone.Size}")
    private String phone;
    @Size(max = 100, message = "{security.current.domain.vo.request.SignInRequest.password.Size}")
    @NotBlank(message = "{security.current.domain.vo.request.SignInRequest.password.NotBlank}")
    private String password;
}
