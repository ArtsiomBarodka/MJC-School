package com.epam.esm.util;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;


/**
 * The type Cookie utils.
 */
public final class CookieUtils {
    /**
     * Gets cookie.
     *
     * @param request the request
     * @param name    the name
     * @return the cookie
     */
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        return findByName(request, name);
    }

    /**
     * Add cookie.
     *
     * @param response the response
     * @param name     the name
     * @param value    the value
     * @param maxAge   the max age
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Delete cookie.
     *
     * @param request  the request
     * @param response the response
     * @param name     the name
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        findByName(request, name)
                .ifPresent(cookie -> {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                });
    }

    private static Optional<Cookie> findByName(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }

    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    /**
     * Deserialize t.
     *
     * @param <T>    the type parameter
     * @param cookie the cookie
     * @param cls    the cls
     * @return the t
     */
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue())));
    }

    private CookieUtils(){

    }
}
