package com.ntd.socialnetwork.post.exception;


import com.ntd.socialnetwork.common.dto.response.APIResponse;
import com.ntd.socialnetwork.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.ntd.socialnetwork.post")
public class PostExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleRuntimeException(RuntimeException ex) {
        log.error("App Exception: ", ex);
        APIResponse<Void> response = new APIResponse<>(
                false,
                ex.getMessage(),
                null,
                ErrorCode.UNCATEGORIZED_ERROR.getCode()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PostException.class)
    public ResponseEntity<APIResponse<Void>> handlePostException(PostException exception) {
        log.info("Post Exception: ", exception);
        PostErrorCode errorCode = exception.getPostErrorCode();
        APIResponse<Void> response = new APIResponse<>(
                false,
                errorCode.getMessage(),
                null,
                errorCode.getCode()
        );

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }
}
