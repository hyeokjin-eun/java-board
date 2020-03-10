package com.user.java.application.api;

import com.common.domain.entity.User;
import com.common.infra.UserRepository;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserApiResponse create(UserApiRequest userApiRequest) {
        User user = userRepository.save(User.builder()
                .email(userApiRequest.getEmail())
                .password(userApiRequest.getPassword())
                .name(userApiRequest.getName())
                .build());

        return UserApiResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();
    }
}
