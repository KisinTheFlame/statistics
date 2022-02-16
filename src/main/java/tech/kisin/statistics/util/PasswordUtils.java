package tech.kisin.statistics.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtils {
    public static String generateSalt() {
        return StringUtils.getRandomString(128);
    }

    public static String generateHashedPassword(String password, String salt) {
        return DigestUtils.sha256Hex(password + salt);
    }
}
