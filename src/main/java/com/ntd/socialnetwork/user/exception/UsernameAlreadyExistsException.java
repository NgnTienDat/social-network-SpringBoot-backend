package com.ntd.socialnetwork.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

}
