package com.user.java.ui.api;

import com.user.java.application.api.UserService;
import com.user.java.domain.ModelAssembler;
import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController{

    private final UserService userService;

    private final ModelAssembler<UserApiResponse> modelAssembler;

    public UserController(UserService userService, ModelAssembler<UserApiResponse> modelAssembler) {
        this.userService = userService;
        this.modelAssembler = modelAssembler;
    }

    @PostMapping("users")
    private ResponseEntity<EntityModel<UserApiResponse>> create(@RequestBody UserApiRequest userApiRequest) {
        UserApiResponse userApiResponse = userService.create(userApiRequest);
        EntityModel<UserApiResponse> userApiResponseEntityModel = modelAssembler.toModel(userApiResponse, userApiResponse.getId(), this.create(userApiRequest));

        return ResponseEntity.created(userApiResponseEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(userApiResponseEntityModel);
    }
}
