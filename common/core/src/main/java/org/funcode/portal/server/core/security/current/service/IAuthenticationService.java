/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.core.security.current.service;

import org.funcode.portal.server.core.base.http.response.ResponseResult;
import org.funcode.portal.server.core.security.current.domain.vo.request.SignInRequest;
import org.funcode.portal.server.core.security.current.domain.vo.request.SignUpRequest;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IAuthenticationService {
    ResponseResult<String> signup(SignUpRequest request);

    ResponseResult<String> signin(SignInRequest request);
}
