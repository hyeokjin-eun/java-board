package com.board.java.application;

import com.board.java.domain.entity.User;
import com.board.java.domain.request.SessionApiRequest;
import com.board.java.domain.response.SessionApiResponse;
import com.board.java.infra.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SessionApiResponse authenticate(SessionApiRequest sessionApiRequest) {
        User user = userRepository.findByEmail(sessionApiRequest.getEmail())
                .orElseThrow(RuntimeException::new);

        if (passwordEncoder.matches(sessionApiRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }
        return null;
    }
}
