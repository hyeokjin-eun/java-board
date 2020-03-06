package com.user.java.application.api;

import com.user.java.application.BaseService;
import com.user.java.domain.exception.UserNotFoundException;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import com.user.java.infra.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceBackUp extends BaseService<UserApiRequest, UserApiResponse, User> {

    private final String USER_PATH = "users";

    @Override
    public EntityModel<UserApiResponse> create(UserApiRequest userApiRequest) {
        User newUser = baseRepository.save(User.builder()
                .email(userApiRequest.getEmail())
                .password(userApiRequest.getPassword())
                .name(userApiRequest.getName())
                .build());

        return getEntityModel(UserApiResponse.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .name(newUser.getName())
                .build());
    }

    @Override
    public EntityModel<UserApiResponse> detail(Long id) {
        return baseRepository.findById(id)
                .map(user -> UserApiResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build()
                )
                .map(this::getEntityModel)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public CollectionModel<EntityModel<UserApiResponse>> list() {
        return getCollectionModel(baseRepository.findAll().stream()
                .map(user -> UserApiResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build()));
    }

    private EntityModel<UserApiResponse> getEntityModel(UserApiResponse userApiResponse) {
        return modelAssembler.toModel(userApiResponse, userApiResponse.getId(), USER_PATH);
    }

    private CollectionModel<EntityModel<UserApiResponse>> getCollectionModel(Stream<UserApiResponse> userApiResponseStream) {
        return modelAssembler.toCollectionModel(
                userApiResponseStream
                        .map(this::getEntityModel)
                        .collect(Collectors.toList()), USER_PATH);
    }
}









