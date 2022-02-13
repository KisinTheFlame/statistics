package tech.kisin.statistics.utils;

import java.security.InvalidParameterException;
import java.util.Random;

public class StringUtils {
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private static final Random random = new Random();

    public static String getRandomString(int length) {
        if(length < 0) throw new InvalidParameterException();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return builder.toString();
    }
}
