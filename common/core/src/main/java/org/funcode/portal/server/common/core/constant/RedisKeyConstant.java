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
public final class RedisKeyConstant {
    /** access-token */
    public static final String TOKEN_KEY = "funcode:security:token:";
    /** refresh-transition-token */
    public static final String TOKEN_TRANSITION_KEY = "funcode:security:transition-token:";
}
