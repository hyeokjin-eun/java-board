package com.user.java.application.api;

import com.common.domain.entity.User;
import com.common.infra.UserRepository;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
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
    @CsvSource("1, email@email.com, password, kim")
    @DisplayName("유저 생성 Service Test")
    void create(Long id, String email, String password, String name) {
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

        given(userRepository.save(any(User.class))).willReturn(User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build());

        UserApiResponse userApiResponse = userService.create(userApiRequest);

        verify(userRepository).save(any(User.class));
        assertThat(userApiResponse.getId()).isEqualTo(id);
        assertThat(userApiResponse.getEmail()).isEqualTo(email);
        assertThat(userApiResponse.getPassword()).isEqualTo(password);
        assertThat(userApiResponse.getName()).isEqualTo(name);
    }
}