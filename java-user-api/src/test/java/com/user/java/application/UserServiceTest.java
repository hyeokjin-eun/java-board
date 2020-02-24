package com.user.java.application;

import com.user.java.domain.request.UserCreateRes;
import com.user.java.domain.response.UserCreateReq;
import com.user.java.infra.User;
import com.user.java.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test.com, test, pack"})
    @DisplayName("User 생성 Service")
    void create(Long id, String email, String password, String name) {
        given(userRepository.save(any(User.class))).willReturn(User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build());

        UserCreateRes userCreateRes = userService.create(UserCreateReq.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());

        verify(userRepository).save(any(User.class));

        assertThat(userCreateRes.getId()).isEqualTo(id);
        assertThat(userCreateRes.getEmail()).isEqualTo(email);
        assertThat(userCreateRes.getPassword()).isEqualTo(password);
        assertThat(userCreateRes.getName()).isEqualTo(name);
    }
}