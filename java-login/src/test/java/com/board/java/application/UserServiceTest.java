package com.board.java.application;

import com.board.java.domain.request.SessionApiRequest;
import com.board.java.domain.response.SessionApiResponse;
import com.board.java.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @CsvSource(value = "email@email.com, password")
    @DisplayName("세션 체크 Service Test")
    void authenticate(String email, String password) {
        SessionApiRequest sessionApiRequest = SessionApiRequest.builder()
                .email(email)
                .password(password)
                .build();

        SessionApiResponse authenticate = userService.authenticate(sessionApiRequest);

        verify(userService).authenticate(any(SessionApiRequest.class));
    }
}