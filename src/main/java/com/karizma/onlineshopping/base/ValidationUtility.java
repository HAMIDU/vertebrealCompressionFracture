package com.karizma.onlineshopping.base;

import java.time.LocalTime;
import java.util.regex.Pattern;


public class ValidationUtility {
    static final Short MINIMUM_PASSWORD_LENGTH = 4;

    public static boolean validatePassword(String password) {
        return Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&^()_~;]{" +
                MINIMUM_PASSWORD_LENGTH + ",}$").matcher(password).matches();
    }

    private ValidationUtility() {
    }

    public static boolean invalidPurchaseTime() {
        return LocalTime.now().isBefore(LocalTime.of(8, 0)) ||
                LocalTime.now().isAfter(LocalTime.of(19, 0));
    }
}
