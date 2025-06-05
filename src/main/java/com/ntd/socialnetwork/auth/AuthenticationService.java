package com.ntd.socialnetwork.auth;

import com.ntd.socialnetwork.auth.dto.request.AuthenticationRequest;
import com.ntd.socialnetwork.auth.dto.response.AuthenticationResponse;
import com.ntd.socialnetwork.user.UserService;
import com.ntd.socialnetwork.user.exception.ErrorCode;
import com.ntd.socialnetwork.user.exception.UserNotFoundException;
import com.ntd.socialnetwork.user.exception.UsernameAlreadyExistsException;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    AuthenticationRepository authenticationRepository;

    AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) {
        User user = authenticationRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOTFOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean isAuthenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        return new AuthenticationResponse(isAuthenticated);
    }
}
