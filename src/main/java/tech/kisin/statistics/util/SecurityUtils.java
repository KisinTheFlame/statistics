package tech.kisin.statistics.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.HttpServletResponse;

public class SecurityUtils {
    public static final String TOKEN_COOKIE_NAME = "token";
    public static final String SUPER_TOKEN = "Nana7miLovesKisinTheFlameForever";

    public static String generateSalt() {
        return StringUtils.getRandomString(128);
    }

    public static String generateHashedPassword(String password, String salt) {
        return DigestUtils.sha256Hex(password + salt);
    }

    public static String generateToken() {
        return StringUtils.getRandomString(128);
    }

    public static void addTokenIntoCookie(HttpServletResponse response, String token, int maxAge) {
        ResponseCookie cookie = ResponseCookie
                .from(TOKEN_COOKIE_NAME, token)
                .maxAge(maxAge)
                .path("/")
                .secure(true)
                .httpOnly(false)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
