package com.ntd.socialnetwork.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
