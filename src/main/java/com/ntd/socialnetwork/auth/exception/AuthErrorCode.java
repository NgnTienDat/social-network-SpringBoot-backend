package com.ntd.socialnetwork.auth.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AuthErrorCode {
    UNCATEGORIZED_ERROR(666, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTS(2002, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(2001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2003, "You dont have permission", HttpStatus.FORBIDDEN),
//    USER_ALREADY_EXISTS(409, "User already exists"),
//    INVALID_MESSAGE_KEY(1000, "Invalid Message Key"),
//    USERNAME_INVALID(1001, "Username must contain at least 8 characters and at most 45 characters"),
//    NOT_BLANK(1002, "Cannot blank this field"),
//    INVALID_EMAIL(1003, "Invalid email address"),
//    INVALID_PASSWORD(1004, "Invalid password"),
//    INVALID_PHONE(1005, "Invalid phone number"),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
