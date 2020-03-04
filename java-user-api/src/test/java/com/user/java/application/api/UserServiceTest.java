package com.user.java.application.api;

import com.user.java.infra.User;
import com.user.java.infra.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
    }

    @ParameterizedTest
    @DisplayName("User Detail Service Test")
    @CsvSource(value = {"1", "2"})
    void detail(Long id) {
        given(userRepository.findById(id)).willReturn(User.builder()
                .id(id)
                .email()
                .password()
                .name()
                .build()
        )
        userService.detail(id);
    }
}