package com.user.java.ui.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.java.application.api.UserService;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
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
                .andExpect(status().isOk())
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
}