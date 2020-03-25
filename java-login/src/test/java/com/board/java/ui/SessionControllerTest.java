package com.board.java.ui;

import com.board.java.application.UserService;
import com.board.java.domain.request.SessionApiRequest;
import com.board.java.domain.response.SessionApiResponse;
import com.board.java.domain.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @CsvSource(value = "email@email.com, password")
    @DisplayName("로그인 Session Create Controller Test")
    void create(String email, String password) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(SessionApiRequest.builder()
                .email(email)
                .password(password)
                .build());

        given(userService.authenticate(any(SessionApiRequest.class))).willReturn(SessionApiResponse.builder()
                .accessToken("eyJhbGciOiJIUzI1NiJ9." +
                        "eyJpZCI6MSwiZW1haWwiOiJlbWFpbEBlbWFpbC5jb20iLCJuYW1lIjoia2ltIn0." +
                        "MC4CfFKE_p50W1snULDmwVYYCw7Wtf-SpBr7Z9nR7i4")
                .build());

        mockMvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("eyJhbGciOiJIUzI1NiJ9." +
                        "eyJpZCI6MSwiZW1haWwiOiJlbWFpbEBlbWFpbC5jb20iLCJuYW1lIjoia2ltIn0." +
                        "MC4CfFKE_p50W1snULDmwVYYCw7Wtf-SpBr7Z9nR7i4"));

        verify(userService).authenticate(any(SessionApiRequest.class));
    }
}