package com.ntd.socialnetwork.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends RuntimeException {
  private ErrorCode errorCode;
    public UserException(ErrorCode errorCode) {
      super(errorCode.getMessage());
      this.errorCode = errorCode;
    }
}
