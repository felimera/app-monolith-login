package com.project.app_login_back.insfraestructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
public final class MessageUtils {

    private static MessageSource instance;

    // 1. Constructor privado para Sonar. Al NO llevar @Component,
    // Spring ya no intentará entrar aquí, por lo que NO lanzará el error.
    private MessageUtils() {
        throw new IllegalStateException("Utility class");
    }

    // 2. Método interno para que el puente le pase el MessageSource de Spring
    public static void initialize(MessageSource messageSource) {
        MessageUtils.instance = messageSource;
    }

    // 3. Tu método estático de siempre queda idéntico
    public static String getMessage(String messageKey, Object... args) {
        if (instance == null) {
            throw new IllegalStateException("MessageSource no ha sido inicializado.");
        }

        Locale locale = LocaleContextHolder.getLocale();
        log.info("El Locale detectado por Spring es: {}", locale.getLanguage());

        return instance.getMessage(messageKey, args, locale);
    }
}

@Component
class MessageUtilsBridge {

    public MessageUtilsBridge(MessageSource messageSource) {
        // Enlazamos el mundo estático con el mundo de Spring sin romper las reglas de Sonar
        MessageUtils.initialize(messageSource);
    }
}