/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtil {

    private static final Pattern username_pattern = Pattern.compile("^[a-z][a-z0-9]$");
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
    private static final Pattern money_pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");

    /**
     * 验证手机号
     *
     * @param src
     * @return
     */
    public static boolean isMobile(String src) {
        if (StringUtils.isBlank(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }

    /**
     * 验证用户名
     *
     * @param username
     * @return
     */
    public static boolean isUsernameValid(String username) {
        if (StringUtils.isBlank(username) || username.length() < 3 || username.length() > 99) {
            return false;
        }
        Matcher m = username_pattern.matcher(username);
        return m.matches();
    }


    /**
     * 验证金额0.00
     *
     * @param money
     * @return
     */
    public static boolean isMoney(BigDecimal money) {
        if (money == null) {
            return false;
        }
        if (!NumberUtils.isCreatable(String.valueOf(money.doubleValue()))) {
            return false;
        }
        if (money.doubleValue() == 0) {
            return false;
        }
        Matcher m = money_pattern.matcher(String.valueOf(money.doubleValue()));
        return m.matches();
    }


    /**
     * 验证 日期
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static boolean isDateTime(String date, String dateFormat) {
        if (StringUtils.isBlank(date)) {
            return false;
        }
        try {
            DateUtils.parseDate(date, dateFormat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
