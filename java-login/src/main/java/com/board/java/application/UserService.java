package com.board.java.application;

import com.board.java.domain.entity.User;
import com.board.java.domain.request.SessionApiRequest;
import com.board.java.domain.response.SessionApiResponse;
import com.board.java.infra.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public SessionApiResponse authenticate(SessionApiRequest sessionApiRequest) {
        User user = userRepository.findByEmail(sessionApiRequest.getEmail())
                .orElseThrow(RuntimeException::new);

        if (passwordEncoder.matches(sessionApiRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }
        return null;
    }
}
