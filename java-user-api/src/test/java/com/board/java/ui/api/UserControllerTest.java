package com.board.java.ui.api;

import com.board.java.application.api.UserService;
import com.board.java.domain.UserAssembler;
import com.board.java.domain.exception.UserNotFoundException;
import com.board.java.domain.request.UserApiRequest;
import com.board.java.domain.response.UserApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(UserAssembler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @CsvSource("1, email@email.com, password, kim")
    @DisplayName("유저 생성 API Controller Test")
    void create(Long id, String email, String password, String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(UserApiRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();

        given(userService.create(any(UserApiRequest.class))).willReturn(userApiResponse);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password))
                .andExpect(jsonPath("$.name").value(name));

        verify(userService).create(any(UserApiRequest.class));
    }

    @ParameterizedTest
    @CsvSource("email, '', email@")
    @DisplayName("유저 생성 API Controller 이메일 Validation Test")
    void createEmailValidationCheck(String email) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(UserApiRequest.builder()
                .email(email)
                .password("password")
                .name("kim")
                .build());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource("''")
    @DisplayName("유저 생성 API Controller 비밀번호 Validation Test")
    void createPasswordValidationCheck(String password) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(UserApiRequest.builder()
                .email("email@email.com")
                .password(password)
                .name("kim")
                .build());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource("''")
    @DisplayName("유저 생성 API Controller 이름 Validation Test")
    void createNameValidationCheck(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(UserApiRequest.builder()
                .email("email@email.com")
                .password("password")
                .name(name)
                .build());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 목록 API Controller Test")
    void list() throws Exception {
        List<UserApiResponse> userApiResponses = Arrays.asList(
                UserApiResponse.builder()
                        .id(1L)
                        .email("email@email.com")
                        .password("password")
                        .name("kim")
                        .build(),
                UserApiResponse.builder()
                        .id(2L)
                        .email("test@test.com")
                        .password("test")
                        .name("pack")
                        .build());

        given(userService.list()).willReturn(userApiResponses);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.userApiResponseList[0].id").value(1L))
                .andExpect(jsonPath("$._embedded.userApiResponseList[1].id").value(2L));

        verify(userService).list();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test.com, test, pack"})
    @DisplayName("유저 상세 API Controller Test")
    void detail(Long id, String email, String password, String name) throws Exception {
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();

        given(userService.detail(id)).willReturn(userApiResponse);

        mockMvc.perform(get("/users/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password))
                .andExpect(jsonPath("$.name").value(name));

        verify(userService).detail(id);
    }

    @ParameterizedTest
    @CsvSource("404")
    @DisplayName("유저 상세 API Controller User Not Found")
    void detailUserNotFound(Long id) throws Exception {
        given(userService.detail(id)).willThrow(new UserNotFoundException());

        mockMvc.perform(get("/users/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService).detail(id);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test.com, test, pack"})
    @DisplayName("유저 수정 API Controller Test")
    void update(Long id, String email, String password, String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(UserApiRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();

        given(userService.update(eq(id), any(UserApiRequest.class))).willReturn(userApiResponse);

        mockMvc.perform(put("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password))
                .andExpect(jsonPath("$.name").value(name));

        verify(userService).update(eq(id), any(UserApiRequest.class));
    }

    @ParameterizedTest
    @CsvSource(value = {"email", "''"})
    @DisplayName("유저 수정 API Controller 이메일 Validation Test")
    void updateEmailValidationCheck(String email) throws Exception {
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .email(email)
                .password("password")
                .name("kim")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userApiRequest);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"''"})
    @DisplayName("유저 수정 API Controller 비밀번호 Validation Test")
    void updatePasswordValidationCheck(String passowrd) throws Exception {
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .email("email@email.com")
                .password(passowrd)
                .name("kim")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userApiRequest);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"''"})
    @DisplayName("유저 수정 API Controller 이름 Validation Test")
    void updateNameValidationCheck(String name) throws Exception {
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .email("email@email.com")
                .password("password")
                .name(name)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userApiRequest);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}