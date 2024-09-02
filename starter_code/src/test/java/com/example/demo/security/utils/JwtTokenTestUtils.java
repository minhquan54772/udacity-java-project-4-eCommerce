package com.example.demo.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.test.context.TestComponent;

import java.util.Date;

import static com.example.demo.security.SecurityTestContants.*;

@TestComponent
public class JwtTokenTestUtils {
    public static String generateJwtToken(String username) {
        String token = JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TEST_JWT_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TEST_JWT_SECRET_KEY));
        return TEST_JWT_TOKEN_PREFIX + token;
    }
}
