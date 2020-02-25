package com.user.java.domain;

import com.user.java.ui.CrudController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModelAssembler<Res> implements RepresentationModelAssembler<Res, EntityModel<Res>> {

    @Override
    public EntityModel<Res> toModel(Res res) {
        return new EntityModel<>(res,
                linkTo(methodOn(CrudController.class).detail(1L)).withSelfRel());
    }

    public CollectionModel<EntityModel<Res>> toCollectionModel(List<EntityModel<Res>> res) {
        return new CollectionModel<>(res,
                linkTo(methodOn(CrudController.class).list()).withSelfRel());
    }
}