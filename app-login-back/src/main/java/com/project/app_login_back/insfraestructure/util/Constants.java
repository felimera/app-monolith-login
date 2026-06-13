package com.project.app_login_back.insfraestructure.util;

public class Constants {
    private Constants() {
        throw new IllegalStateException(Constants.class.toString());
    }

    public static final String CODE_ROL_ADMIN = "ADMIN";
    public static final String CODE_ROL_DIRECT = "DIRECT";
    public static final String CODE_ROL_CLIENT = "CLIENT";

    public static final String E = "E";
    public static final String U = "U";

    public static final String CONFIG_MESSAGE = "config.message";
    public static final String MESSAGE_CREDENTIALS_USERPASS = "config.message.credentials.userpass";
    public static final String CONFIG_TOKEN = "config.token";
    public static final String ILLEGAL_STATUS_ERROR = "config.illegal.status.error";
    public static final String UNAUTHORIZED_ACCESS_ATTEMPT = "config.unauthorized.access.attempt";
    public static final String YOU_NOT_SUFF_PERMISSIONS_ACTION = "config.you.not.suff.permissions.action";
    public static final String RECURSO_NO_ENCONTRADO = "config.resource.not.found";
    public static final String ERROR_DE_TIEMPO_DE_EJECUCION = "config.runtime.error";
    public static final String ERROR = "config.error";
    public static final String DATO_INVALIDO = "config.invalid.data";

    public static final String REQUESTED_PERMISSION_ACCESS_TOKEN = "config.requested.permission.access.token";
    public static final String ERROR_LOGGED = "config.error.logged";
    public static final String UNEXPECTED_ERROR_SERVER = "config.unexpected.error.server";
}
