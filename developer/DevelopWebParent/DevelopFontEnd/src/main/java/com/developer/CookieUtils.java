package com.developer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    /**
     * Get the cookie value by cookie name
     *
     * @param request    The HTTP servlet request
     * @param cookieName The cookie name
     * @return The cookie value
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Create a cookie with the given name and value
     *
     * @param response   The HTTP servlet response
     * @param cookieName The cookie name
     * @param value      The cookie value
     * @param maxAge     The maximum age of the cookie in seconds
     */
    public static void createCookie(HttpServletResponse response, String cookieName, String value, int maxAge) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Delete the cookie with the given name
     *
     * @param response   The HTTP servlet response
     * @param cookieName The cookie name
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

