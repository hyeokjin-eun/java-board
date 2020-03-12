package com.board.java.ui.api;

import com.board.java.application.api.UserService;
import com.board.java.domain.UserAssembler;
import com.board.java.domain.request.UserApiRequest;
import com.board.java.domain.response.UserApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("users")
public class UserController{

    private final UserService userService;

    private final UserAssembler userAssembler;

    public UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Validated @RequestBody UserApiRequest userApiRequest) {
        UserApiResponse response = userAssembler.toModel(userService.create(userApiRequest));
        return ResponseEntity.created(linkTo(methodOn(UserController.class).detail(response.getId())).toUri())
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> list() {
        CollectionModel<UserApiResponse> responses = userAssembler.toCollectionModel(userService.list());
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        UserApiResponse response = userAssembler.toModel(userService.detail(id));
        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Validated @RequestBody UserApiRequest userApiRequest) {
        UserApiResponse response = userAssembler.toModel(userService.update(id, userApiRequest));
        return ResponseEntity.ok()
                .body(response);
    }
}
