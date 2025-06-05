package com.ntd.socialnetwork.user.exception;

import com.ntd.socialnetwork.user.dto.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice(basePackages = {"com.ntd.socialnetwork.user", "com.ntd.socialnetwork.auth"})
public class UserExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleRuntimeException(RuntimeException ex) {

        APIResponse<Void> response = new APIResponse<>(
                false,
                ex.getMessage(),
                null,
                ErrorCode.UNCATEGORIZED_ERROR.getCode()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleUserNotFound() {

        APIResponse<Void> response = new APIResponse<>(
                false,
                ErrorCode.USER_NOTFOUND.getMessage(),
                null,
                ErrorCode.USER_NOTFOUND.getCode()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<APIResponse<Void>> handleUsernameExists() {
        APIResponse<Void> response = new APIResponse<>(
                false,
                ErrorCode.USER_ALREADY_EXISTS.getMessage(),
                null,
                ErrorCode.USER_ALREADY_EXISTS.getCode()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
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


}
