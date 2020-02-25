package com.user.java.application.api;

import com.user.java.application.BaseService;
import com.user.java.domain.exception.UserNotFoundException;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import com.user.java.infra.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Override
    public UserApiResponse create(UserApiRequest userApiRequest) {
        return null;
    }

    @Override
    public UserApiResponse detail(Long id) {
        return baseRepository.findById(id)
                .map(user -> UserApiResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build()
                )
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<EntityModel<UserApiResponse>> list() {
        return ;
    }
}
