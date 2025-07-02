package com.ntd.socialnetwork.auth.exception;


import com.ntd.socialnetwork.auth.dto.response.AuthAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice(basePackages = "com.ntd.socialnetwork.auth")
public class AuthExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AuthAPIResponse<Void>> handleRuntimeException(RuntimeException ex) {

        AuthAPIResponse<Void> response = new AuthAPIResponse<>(
                false,
                ex.getMessage(),
                null,
                AuthErrorCode.UNCATEGORIZED_ERROR.getCode()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthAPIResponse<Void>> handleAuthException(AuthException exception) {

        AuthErrorCode errorCode = exception.getErrorCode();
        AuthAPIResponse<Void> response = new AuthAPIResponse<>(
                false,
                errorCode.getMessage(),
                null,
                errorCode.getCode()
        );

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AuthAPIResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {

        AuthErrorCode errorCode = AuthErrorCode.UNAUTHORIZED;
        AuthAPIResponse<Void> response = new AuthAPIResponse<>(
                false,
                errorCode.getMessage(),
                null,
                errorCode.getCode()
        );

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }
}
