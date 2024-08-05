package org.funcode.portal.server.common.core.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtils {

    /**
     * 获取指定Cookie的值
     *
     * @param request  HttpServletRequest
     * @param cookieName  cookie name
     * @return cookie value
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }
}
