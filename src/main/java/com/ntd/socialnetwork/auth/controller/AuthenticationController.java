package com.ntd.socialnetwork.auth.controller;


import com.nimbusds.jose.JOSEException;
import com.ntd.socialnetwork.auth.dto.request.LogoutRequest;
import com.ntd.socialnetwork.auth.dto.request.RefreshRequest;
import com.ntd.socialnetwork.auth.service.AuthenticationService;
import com.ntd.socialnetwork.auth.dto.request.AuthenticationRequest;
import com.ntd.socialnetwork.auth.dto.request.IntrospectRequest;
import com.ntd.socialnetwork.auth.dto.response.AuthenticationResponse;
import com.ntd.socialnetwork.auth.dto.response.IntrospectResponse;
import com.ntd.socialnetwork.common.dto.response.APIResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.text.ParseException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;


    // login
    @PostMapping("/token")
    public ResponseEntity<APIResponse<AuthenticationResponse>> authenticate(
            @RequestBody  AuthenticationRequest authenticationRequest) {
        AuthenticationResponse result = authenticationService.authenticated(authenticationRequest);
        APIResponse<AuthenticationResponse> response = new APIResponse<>(
                true,
                "is authenticated",
                result,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<APIResponse<IntrospectResponse>> authenticate(
            @RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {

        IntrospectResponse result = authenticationService.introspect(introspectRequest);
        APIResponse<IntrospectResponse> response = new APIResponse<>(
                true,
                "Verify token",
                result,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public APIResponse<Void> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        this.authenticationService.logout(logoutRequest);
        return APIResponse.<Void>builder()
                .message("Logout")
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }


    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<AuthenticationResponse>> authenticate(
            @RequestBody RefreshRequest refreshRequest) throws ParseException, JOSEException {
        AuthenticationResponse result = authenticationService.refreshToken(refreshRequest);
        APIResponse<AuthenticationResponse> response = new APIResponse<>(
                true,
                "is authenticated",
                result,
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
