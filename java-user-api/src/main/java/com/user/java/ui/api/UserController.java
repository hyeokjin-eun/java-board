package com.user.java.ui.api;

import com.user.java.application.api.UserService;
import com.user.java.domain.request.UserApiRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Validated @RequestBody UserApiRequest userApiRequest) {
        return ResponseEntity.ok()
                .body(userService.create(userApiRequest));
    }

}
