package com.board.java.application.api;

import com.board.java.domain.entity.User;
import com.board.java.domain.exception.UserNotFoundException;
import com.board.java.domain.request.UserApiRequest;
import com.board.java.domain.response.UserApiResponse;
import com.board.java.infra.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserApiResponse> list() {
        return userRepository.findAll().stream()
                .map(user -> UserApiResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public UserApiResponse detail(Long id) {
        return userRepository.findById(id)
                .map(user -> UserApiResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build()
                )
                .orElseThrow(UserNotFoundException::new);
    }

    public UserApiResponse update(Long id, UserApiRequest userApiRequest) {
        return null;
    }
}
