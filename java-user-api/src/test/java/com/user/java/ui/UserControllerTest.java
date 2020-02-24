package com.user.java.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.java.application.UserService;
import com.user.java.domain.request.UserCreateRes;
import com.user.java.domain.response.UserCreateReq;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim", "2, test@test, test, pack"})
    void create(Long id, String email, String password, String name) throws Exception {
        UserCreateRes userCreateRes = UserCreateRes.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(UserCreateReq.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());

        given(userService.create(any(UserCreateReq.class))).willReturn(userCreateRes);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("password").value(password))
                .andExpect(jsonPath("name").value(name));

        verify(userService).create(any(UserCreateReq.class));
    }
}