/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstant {
    /** token存放的请求头key */
    public static final String TOKEN_HEADER_KEY = "FA";
    /** token存放的cookie的key */
    public static final String TOKEN_COOKIE_KEY = "funcode-token";
}
