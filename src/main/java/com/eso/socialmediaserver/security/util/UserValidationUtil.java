package com.eso.socialmediaserver.security.util;

import java.util.regex.Pattern;

public class UserValidationUtil {

    private UserValidationUtil() {}

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
}