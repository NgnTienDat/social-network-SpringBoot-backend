package com.ntd.socialnetwork.post.exception;

import com.ntd.socialnetwork.user.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostException extends RuntimeException {
    private PostErrorCode postErrorCode;
    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.postErrorCode = errorCode;
    }
}
