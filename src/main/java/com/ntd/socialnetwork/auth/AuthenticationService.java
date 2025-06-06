package com.ntd.socialnetwork.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.ntd.socialnetwork.auth.dto.request.AuthenticationRequest;
import com.ntd.socialnetwork.auth.dto.request.IntrospectRequest;
import com.ntd.socialnetwork.auth.dto.response.AuthenticationResponse;
import com.ntd.socialnetwork.auth.dto.response.IntrospectResponse;
import com.ntd.socialnetwork.auth.exception.AuthErrorCode;
import com.ntd.socialnetwork.auth.exception.AuthException;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    AuthenticationRepository authenticationRepository;
    @NonFinal
    @Value("${auth.signer-key}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiration.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) {
        User user = authenticationRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_EXISTS));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean isAuthenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!(isAuthenticated)) throw new AuthException(AuthErrorCode.UNAUTHENTICATED);

        var token = generateToken(authenticationRequest.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();


    }

    public String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("ntd.socialnetwork.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                ))
                .claim("authorities", "ROLE_USER")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create JWT token", e);
            throw new RuntimeException(e);
        }
    }
}
