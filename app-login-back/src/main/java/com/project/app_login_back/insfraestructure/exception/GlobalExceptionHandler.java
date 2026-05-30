package com.project.app_login_back.insfraestructure.exception;

import com.project.app_login_back.application.dto.error.ErrorResponse;
import com.project.app_login_back.insfraestructure.util.Constants;
import com.project.app_login_back.insfraestructure.util.MessageUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
@Slf4j
public class GlobalExceptionHandler {

    // Error cuando las credenciales son incorrectas (401)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        String mensajeTraducido = MessageUtils.getMessage(Constants.MESSAGE_CREDENTIALS_USERPASS);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                mensajeTraducido,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Error cuando el usuario ya existe en el registro (409)
    @ExceptionHandler(UserAlreadyExistsException.class) // Debes crear esta excepción personalizada
    public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
        log.error(Constants.ILLEGAL_STATUS_ERROR, ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(), // 403 Prohibido, ya que no tiene roles
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        log.warn(Constants.UNAUTHORIZED_ACCESS_ATTEMPT, ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                Constants.YOU_NOT_SUFF_PERMISSIONS_ACTION,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        log.error(Constants.RECURSO_NO_ENCONTRADO, ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                Constants.REQUESTED_PERMISSION_ACCESS_TOKEN,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        // Es vital loguear el error real en la consola de IntelliJ para que tú sepas qué pasó
        log.error(Constants.ERROR_DE_TIEMPO_DE_EJECUCION, ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // O INTERNAL_SERVER_ERROR (500) según prefieras
                ex.getMessage(), // Aquí viajará el mensaje "Se encontró más de un usuario..."
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidData(InvalidDataException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(Constants.ERROR, Constants.DATO_INVALIDO);
        response.put("message", ex.getMessage()); // Aquí irá "El email no es válido"

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Error genérico para cualquier otra falla (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex) {
        log.error(Constants.ERROR_LOGGED, ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Constants.UNEXPECTED_ERROR_SERVER,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

