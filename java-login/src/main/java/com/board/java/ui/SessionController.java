package com.board.java.ui;

import com.board.java.application.UserService;
import com.board.java.domain.request.SessionApiRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("session")
public class SessionController {

    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Validated @RequestBody SessionApiRequest sessionApirequest) throws URISyntaxException {
        return ResponseEntity.created(new URI("/session"))
                .body(userService.authenticate(sessionApirequest));
    }
}
