package com.project.app_login_back.insfraestructure.util;

public class CadenaUtil {
    private CadenaUtil() {
        throw new IllegalStateException(CadenaUtil.class.toString());
    }

    public static String getIdentifyEmailOrUsername(String valor) {
        if (valor.contains("@"))
            return Constants.E;
        else
            return Constants.U;
    }
}
