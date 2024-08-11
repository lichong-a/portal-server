/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.base.http.handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.funcode.portal.server.common.core.base.exception.BusinessException;
import org.funcode.portal.server.common.core.base.http.response.ResponseResult;
import org.funcode.portal.server.common.core.base.http.response.ResponseStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * exception handler for bad signup.
     *
     * @param e exception
     * @return ResponseResult
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseResult<ExceptionData> handleParameterVerificationException(Exception e) {
        ExceptionData.ExceptionDataBuilder exceptionDataBuilder = ExceptionData.builder();
        log.warn("Exception: {}", e.getMessage());
        if (e instanceof BindException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            bindingResult.getAllErrors()
                    .forEach(a -> exceptionDataBuilder.error(((FieldError) a).getField() + ": " + a.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException) {
            if (e.getMessage() != null) {
                exceptionDataBuilder.error(e.getMessage());
            }
        } else {
            exceptionDataBuilder.error("invalid parameter");
        }
        return ResponseResult.fail(exceptionDataBuilder.build(), "invalid parameter");
    }

    /**
     * handle badCredentialsException.
     *
     * @param badCredentialsException exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseResult<BadCredentialsException> processBusinessException(BadCredentialsException badCredentialsException) {
        log.error(badCredentialsException.getLocalizedMessage(), badCredentialsException);
        return ResponseResult.fail(badCredentialsException.getLocalizedMessage(), ResponseStatusEnum.LOGIN_USERNAME_PASSWORD_INVALID);
    }

    /**
     * handle business exception.
     *
     * @param businessException business exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<BusinessException> processBusinessException(BusinessException businessException) {
        log.error(businessException.getLocalizedMessage(), businessException);
        // 这里可以屏蔽掉后台的异常栈信息，直接返回"business error"
        return ResponseResult.fail(businessException.getLocalizedMessage());
    }

    /**
     * handle other exception.
     *
     * @param exception exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<Exception> processException(Exception exception) {
        log.error(exception.getLocalizedMessage(), exception);
        // 这里可以屏蔽掉后台的异常栈信息，直接返回"server error"
        return ResponseResult.fail(exception.getLocalizedMessage());
    }
}
