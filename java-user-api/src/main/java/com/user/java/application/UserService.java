package com.user.java.application;

import com.user.java.domain.request.UserCreateRes;
import com.user.java.domain.response.UserCreateReq;
import com.user.java.infra.User;
import com.user.java.infra.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCreateRes create(UserCreateReq userCreateReq) {
        User user =  User.builder()
                .email(userCreateReq.getEmail())
                .password(userCreateReq.getPassword())
                .name(userCreateReq.getName())
                .build();

        User newUser = userRepository.save(user);

        return UserCreateRes.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .name(newUser.getName())
                .build();
    }
}
