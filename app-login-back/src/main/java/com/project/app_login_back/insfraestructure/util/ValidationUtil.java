package com.project.app_login_back.insfraestructure.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private ValidationUtil() {
        throw new IllegalStateException(ValidationUtil.class.toString());
    }

    public static String getIdentifyEmailOrUsername(String valor) {
        if (valor.contains("@"))
            return Constants.E;
        else
            return Constants.U;
    }

    public static boolean isEmailValid(String email) {
        if (email == null) return false;
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }
}
