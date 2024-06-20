/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.base.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.funcode.portal.server.common.core.base.validator.IsPhoneValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneValidator.class}) // 标明由哪个类执行校验逻辑
public @interface IsPhone {

    // 校验出错时默认返回的消息
    String message() default "手机号格式错误";

    //分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
