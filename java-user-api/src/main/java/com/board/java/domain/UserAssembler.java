package com.board.java.domain;

import com.board.java.domain.response.UserApiResponse;
import com.board.java.ui.api.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserApiResponse, UserApiResponse> {

    @Override
    public UserApiResponse toModel(UserApiResponse response) {
        response.add(linkTo(methodOn(UserController.class).detail(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(UserController.class).list()).withRel("users"));
        return response;
    }

    public CollectionModel<UserApiResponse> toCollectionModel(List<UserApiResponse> responses) {
        return new CollectionModel<>(responses.stream().map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(UserController.class).list()).withSelfRel());
    }
}
