package com.ntd.socialnetwork.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthAPIResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int code;
}
