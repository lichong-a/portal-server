/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.base.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {
    SUCCESS("200", "success"),
    FAIL("500", "failed"),
    LOGIN_USERNAME_PASSWORD_INVALID("401", "用户名或密码不正确"),

    HTTP_STATUS_200("200", "ok"),
    HTTP_STATUS_400("400", "request error"),
    HTTP_STATUS_401("401", "no authentication"),
    HTTP_STATUS_403("403", "no authorities"),
    HTTP_STATUS_500("500", "server error");

    /**
     * response code
     */
    private final String responseCode;

    /**
     * description.
     */
    private final String description;
}
