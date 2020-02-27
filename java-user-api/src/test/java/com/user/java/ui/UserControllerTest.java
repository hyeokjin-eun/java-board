package com.user.java.ui;

import com.user.java.application.BaseService;
import com.user.java.application.api.UserService;
import com.user.java.domain.ModelAssembler;
import com.user.java.domain.exception.UserNotFoundException;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import com.user.java.infra.User;
import com.user.java.ui.api.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseService<UserApiRequest, UserApiResponse, User> baseService;

    @Autowired
    private ModelAssembler<UserApiResponse> modelAssembler;

    @ParameterizedTest
    @CsvSource(value = {"1, email@email.com, password, kim"})
    @DisplayName("User Detail Controller Test")
    void create(Long id, String email, String password, String name) throws Exception {
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .build();

        EntityModel<UserApiResponse> mockEntityModel = new EntityModel<>(userApiResponse,
                linkTo(methodOn(CrudController.class).detail(userApiResponse.getId())).withSelfRel(),
                linkTo(methodOn(CrudController.class).list()).withRel("list"));

        given(baseService.detail(id)).willReturn(userApiResponse);
        given(modelAssembler.toModel(userApiResponse)).willReturn(mockEntityModel);

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk());

        verify(baseService).detail(id);
    }

    @ParameterizedTest
    @CsvSource(value = {"404"})
    @DisplayName("User Detail Controller Not Found")
    void create(Long id) throws Exception {
        given(baseService.detail(id)).willThrow(UserNotFoundException.class);

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isNotFound());

        verify(baseService).detail(id);
    }

    @Test
    @DisplayName("User List Controller Test")
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
                        .build(),
                UserApiResponse.builder()
                        .id(3L)
                        .email("alvin@alvin.com")
                        .password("alvin")
                        .name("alvin")
                        .build()
        );

        CollectionModel<EntityModel<UserApiResponse>> mockCollectionModel = new CollectionModel<>(userApiResponses.stream()
                .map(modelAssembler::toModel).collect(Collectors.toList()),
                linkTo(methodOn(CrudController.class).list()).withSelfRel());

        given(baseService.list()).willReturn(userApiResponses);
        given(modelAssembler.toCollectionModel(userApiResponses)).willReturn(mockCollectionModel);

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk());


    }
}
