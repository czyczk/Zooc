package com.zzzz.controller.util;

import com.zzzz.dto.UserParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class CookieUtil {
    private static final int DEFAULT_EXPIRY = 7 * 24 * 60 * 60;

    /**
     * Add user login cookie
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param userId User ID
     * @param password Password
     * @param rememberMe Remember me or not
     */
    public static void addUserLoginCookie(HttpServletRequest req, HttpServletResponse resp,
                                          long userId, String password, boolean rememberMe) {
        Cookie userIdCookie = new Cookie("loginUserId", Long.toString(userId));
        Cookie passwordCookie = new Cookie("loginPassword", password);
        List<Cookie> cookies = Arrays.asList(userIdCookie, passwordCookie);
        setPaths(cookies, req.getContextPath() + "/");
        setMaxAges(cookies, rememberMe, DEFAULT_EXPIRY);
        addCookies(resp, cookies);
    }

    public static UserParam parseUserLoginCookie(HttpServletRequest req) {
        UserParam userParam = new UserParam();
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            Arrays.stream(cookies).forEach(it -> {
                if (it.getName().equals("loginUserId"))
                    userParam.setUserId(it.getValue());
                else if (it.getName().equals("loginPassword"))
                    userParam.setPassword(it.getValue());
            });
        }
        return userParam;
    }

    private static void setPaths(List<Cookie> cookies, String path) {
        cookies.parallelStream().forEach(it -> it.setPath(path));
    }

    private static void addCookies(HttpServletResponse resp, List<Cookie> cookies) {
        cookies.forEach(resp::addCookie);
    }

    // Set max ages.
    // Do not save cookies if the user does not wish to be remembered.
    private static void setMaxAges(List<Cookie> cookies, boolean rememberMe, int expiry) {
        cookies.parallelStream().forEach(it -> it.setMaxAge(rememberMe ? expiry : 0));
    }
}
