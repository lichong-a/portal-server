/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service;

import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.security.domain.vo.signup.SignUpRequest;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IAuthenticationService {

    /**
     * 注册
     *
     * @param request 注册请求
     * @return 注册结果
     */
    ResponseResult<String> signup(SignUpRequest request);
}
