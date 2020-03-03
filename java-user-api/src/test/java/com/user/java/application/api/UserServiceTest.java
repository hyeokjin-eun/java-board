package com.user.java.application.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @DisplayName("User Detail Service Test")
    @CsvSource(value = {"1", "2"})
    void detail(Long id) {
        userService.detail(id);
    }
}