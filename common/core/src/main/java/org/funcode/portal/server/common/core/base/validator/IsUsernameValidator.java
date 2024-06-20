/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.funcode.portal.server.common.core.base.validator.annotation.IsUsernameValid;
import org.funcode.portal.server.common.core.util.ValidatorUtil;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public class IsUsernameValidator implements ConstraintValidator<IsUsernameValid, String> {

    /**
     * 用于初始化注解上的值到这个validator
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsUsernameValid constraintAnnotation) {
        // empty
    }

    /**
     * 具体的校验逻辑
     *
     * @param value
     * @param context
     * @return
     */
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidatorUtil.isUsernameValid(value);
    }

}
