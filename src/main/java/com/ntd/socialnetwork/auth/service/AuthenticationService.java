package com.ntd.socialnetwork.auth.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.ntd.socialnetwork.auth.dto.request.AuthenticationRequest;
import com.ntd.socialnetwork.auth.dto.request.IntrospectRequest;
import com.ntd.socialnetwork.auth.dto.request.LogoutRequest;
import com.ntd.socialnetwork.auth.dto.request.RefreshRequest;
import com.ntd.socialnetwork.auth.dto.response.AuthenticationResponse;
import com.ntd.socialnetwork.auth.dto.response.IntrospectResponse;
import com.ntd.socialnetwork.auth.exception.AuthErrorCode;
import com.ntd.socialnetwork.auth.exception.AuthException;
import com.ntd.socialnetwork.auth.model.InvalidatedToken;
import com.ntd.socialnetwork.auth.repository.AuthenticationRepository;
import com.ntd.socialnetwork.auth.repository.InvalidatedTokenRepository;
import com.ntd.socialnetwork.user.model.Role;
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
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    AuthenticationRepository authenticationRepository;
    InvalidatedTokenRepository tokenRepository;

    @NonFinal
    @Value("${auth.signer-key}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${auth.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${auth.refreshable-duration}")
    protected long REFRESH_DURATION;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AuthException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }


    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) {
        log.info("Signer key: {}", SIGNER_KEY);

        User user = authenticationRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_EXISTS));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean isAuthenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!(isAuthenticated)) throw new AuthException(AuthErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();


    }

    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        try {

            var signToken = verifyToken(logoutRequest.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = new InvalidatedToken(jit, expiryTime);
            tokenRepository.save(invalidatedToken);
        }
        catch (AuthException e) {
            log.info("Token is expired");
        }

    }

    // first version, problem: interval loop refresh token
    public AuthenticationResponse refreshToken(RefreshRequest refreshRequest)
            throws ParseException, JOSEException {
        var signJWT = verifyToken(refreshRequest.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jit, expiryTime);

        tokenRepository.save(invalidatedToken);

        String username = signJWT.getJWTClaimsSet().getSubject();
        var user = authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(AuthErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = (isRefresh)?new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                    :signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiration.after(new Date())))
            throw new AuthException(AuthErrorCode.UNAUTHENTICATED);

        if (this.tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AuthException(AuthErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }




    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ntd.socialnetwork.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
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

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                        stringJoiner.add("ROLE_" + role.getName());
                        if (!CollectionUtils.isEmpty(role.getPermissions()))
                            role.getPermissions().forEach(permission -> {
                                stringJoiner.add(permission.getName());
                            });
                    }
            );
        return stringJoiner.toString();
    }
//    private String buildScope(User user) {
//        return user.getRoles().stream()
//                .map(Role::getName)
//                .collect(Collectors.joining(" "));
//    }

}
