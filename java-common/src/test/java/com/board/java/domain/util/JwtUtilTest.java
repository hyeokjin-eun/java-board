package com.board.java.domain.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        this.jwtUtil = new JwtUtil("jwt-token-create-test-secret-key");
    }

    @ParameterizedTest
    @CsvSource("1, email@email.com, kim")
    void createToken(Long id, String email, String name) {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJpZCI6MSwiZW1haWwiOiJlbWFpbEBlbWFpbC5jb20iLCJuYW1lIjoia2ltIn0." +
                "MC4CfFKE_p50W1snULDmwVYYCw7Wtf-SpBr7Z9nR7i4";

        String token = jwtUtil.createToken(id, email, name);

        assertThat(token).isEqualTo(jwtToken);
    }

    @ParameterizedTest
    @CsvSource("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJlbWFpbEBlbWFpbC5jb20iLCJuYW1lIjoia2ltIn0.MC4CfFKE_p50W1snULDmwVYYCw7Wtf-SpBr7Z9nR7i4")
    void getClaims(String token) {
        Claims claims = jwtUtil.getClaims(token);

        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        String name = claims.get("name", String.class);

        assertThat(id).isEqualTo(1);
        assertThat(email).isEqualTo("email@email.com");
        assertThat(name).isEqualTo("kim");
    }
}