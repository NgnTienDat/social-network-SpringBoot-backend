package com.ntd.socialnetwork.user.exception;


import com.ntd.socialnetwork.common.dto.response.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice(basePackages = "com.ntd.socialnetwork.user")
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleRuntimeException(RuntimeException ex) {
        log.error("User Exception: ", ex);
        APIResponse<Void> response = new APIResponse<>(
                false,
                ex.getMessage(),
                null,
                ErrorCode.UNCATEGORIZED_ERROR.getCode()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserException.class)
    public ResponseEntity<APIResponse<Void>> handleUserException(UserException exception) {
        log.error("User Exception 1: ", exception);

        ErrorCode errorCode = exception.getErrorCode();
        APIResponse<Void> response = new APIResponse<>(
                false,
                errorCode.getMessage(),
                null,
                errorCode.getCode()
        );

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }





    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String key = error.getDefaultMessage();

            ErrorCode errorCode;
            try {
                errorCode = ErrorCode.valueOf(key.trim()); // tránh lỗi do khoảng trắng
            } catch (IllegalArgumentException | NullPointerException e) {
                errorCode = ErrorCode.INVALID_MESSAGE_KEY;
            }

            errors.put(error.getField(), errorCode.getMessage());
        }

        APIResponse<Map<String, String>> response = new APIResponse<>(
                false,
                "Validation failed",
                errors,
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<Void>> handleUserAccessDeniedException(AccessDeniedException exception) {

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        APIResponse<Void> response = new APIResponse<>(
                false,
                errorCode.getMessage(),
                null,
                errorCode.getCode()
        );

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }


}
