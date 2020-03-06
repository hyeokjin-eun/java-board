package com.user.java.application.api;

import com.user.java.infra.User;
import com.user.java.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private UserServiceBackUp userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceBackUp();
    }

    @ParameterizedTest
    @DisplayName("User Detail Service Test")
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test.com, test, pack"})
    void detail(Long id, String email, String password, String name) {
        given(userRepository.findById(id)).willReturn(Optional.of(User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build()
        ));

        userService.detail(id);
    }
}