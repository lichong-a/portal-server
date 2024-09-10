/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.base.http.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Builder
@Schema(description = "统一返回结果")
public class ResponseResult<T> {

    /**
     * response timestamp.
     */
    @Schema(description = "时间戳")
    private long timestamp;

    /**
     * response code, 200 -> OK.
     */
    @Schema(description = "状态码")
    private String status;

    /**
     * response message.
     */
    @Schema(description = "消息")
    private String message;

    /**
     * response data.
     */
    @Schema(description = "数据")
    private T data;

    /**
     * response success result wrapper.
     *
     * @param <T> type of data class
     * @return response result
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * response success result wrapper.
     *
     * @param data response data
     * @param <T>  type of data class
     * @return response result
     */
    public static <T> ResponseResult<T> success(T data) {
        return ResponseResult.<T>builder().data(data)
                .message(ResponseStatusEnum.SUCCESS.getDescription())
                .status(ResponseStatusEnum.SUCCESS.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * response error result wrapper.
     *
     * @param message error message
     * @param <T>     type of data class
     * @return response result
     */
    public static <T extends Serializable> ResponseResult<T> fail(String message) {
        return fail(null, message);
    }

    /**
     * response error result wrapper.
     *
     * @param data    response data
     * @param message error message
     * @param <T>     type of data class
     * @return response result
     */
    public static <T> ResponseResult<T> fail(T data, String message) {
        return ResponseResult.<T>builder().data(data)
                .message(message)
                .status(ResponseStatusEnum.FAIL.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * response error result wrapper.
     *
     * @param message error message
     * @param status  error status
     * @param <T>     type of data class
     * @return response result
     */
    public static <T> ResponseResult<T> fail(String message, ResponseStatusEnum status) {
        return fail(null, message, status);
    }

    /**
     * response error result wrapper.
     *
     * @param data    response data
     * @param message error message
     * @param status  error status
     * @param <T>     type of data class
     * @return response result
     */
    public static <T> ResponseResult<T> fail(T data, String message, ResponseStatusEnum status) {
        return ResponseResult.<T>builder().data(data)
                .message(message)
                .status(status.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

}
