package com.user.java.ui;

import com.user.java.application.UserService;
import com.user.java.domain.request.UserCreateRes;
import com.user.java.domain.response.UserCreateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserCreateRes> create(@RequestBody @Valid UserCreateReq userCreateReq) throws URISyntaxException {
        return ResponseEntity.created(new URI("/user"))
                .body(userService.create(userCreateReq));
    }
}
