package com.board.java.application.api;

import com.board.java.domain.entity.User;
import com.board.java.domain.request.UserApiRequest;
import com.board.java.domain.response.UserApiResponse;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("유저 목록 Service Test")
    void list() {
        List<User> users = Arrays.asList(
                User.builder()
                        .id(1L)
                        .email("email@email.com")
                        .password("password")
                        .name("kim")
                        .build(),
                User.builder()
                        .id(2L)
                        .email("test@test.com")
                        .password("test")
                        .name("pack")
                        .build());

        given(userRepository.findAll()).willReturn(users);

        List<UserApiResponse> userApiResponses = userService.list();

        verify(userRepository).findAll();
        assertThat(userApiResponses.get(0).getId()).isEqualTo(1L);
        assertThat(userApiResponses.get(1).getId()).isEqualTo(2L);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test.com, test, pack"})
    @DisplayName("유저 상세 Service Test")
    void detail(Long id, String email, String password, String name) {
        User user = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(user));

        UserApiResponse userApiResponse = userService.detail(id);

        verify(userRepository).findById(id);
        assertThat(userApiResponse.getId()).isEqualTo(id);
        assertThat(userApiResponse.getEmail()).isEqualTo(email);
        assertThat(userApiResponse.getPassword()).isEqualTo(password);
        assertThat(userApiResponse.getName()).isEqualTo(name);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test.com, test, pack"})
    @DisplayName("유저 수정 Service Test")
    void update(Long id, String email, String password, String name) {
        given(userRepository.findById(id)).willReturn(Optional.of(User.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build()));

        given(userRepository.save(any(User.class))).willReturn(User.builder()
                .id(id)
                .email("update@update.com")
                .password("update")
                .name("alvin")
                .build());

        UserApiResponse userApiResponse = userService.update(id, UserApiRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());

        verify(userRepository).findById(id);
        verify(userRepository).save(any(User.class));
        assertThat(userApiResponse.getId()).isEqualTo(id);
        assertThat(userApiResponse.getEmail()).isEqualTo("update@update.com");
        assertThat(userApiResponse.getPassword()).isEqualTo("update");
        assertThat(userApiResponse.getName()).isEqualTo("alvin");
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "2"})
    @DisplayName("유저 삭제 Service Test")
    void delete(Long id) {
        given(userRepository.findById(id)).willReturn(Optional.of(User.builder()
                .id(id)
                .email("email@email.com")
                .password("password")
                .name("kim")
                .build()));

        String response = userService.delete(id);

        verify(userRepository).findById(id);
        verify(userRepository).delete(any(User.class));
        assertThat(response).isEqualTo("{}");
    }
}