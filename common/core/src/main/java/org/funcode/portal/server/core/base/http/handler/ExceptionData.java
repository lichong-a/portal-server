/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.core.base.http.handler;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Builder
public class ExceptionData {

    @Singular
    private final List<Object> errors;
}
