package com.ntd.socialnetwork.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PostErrorCode {
    UNCATEGORIZED_ERROR(666, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    POST_NOT_FOUND(3000, "Post Not Found", HttpStatus.NOT_FOUND),

    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
