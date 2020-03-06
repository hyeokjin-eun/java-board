package com.user.java.domain;

import com.user.java.ui.CrudController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ModelAssembler<Res> implements RepresentationModelAssembler<Res, EntityModel<Res>> {

    @Override
    public EntityModel<Res> toModel(Res res) {
        return null;
    }

    public CollectionModel<EntityModel<Res>> toCollectionModel(List<EntityModel<Res>> res, String path) {
        return new CollectionModel<>(res,
                linkTo(CrudController.class).slash(path).withSelfRel());
    }

    public EntityModel<Res> toModel(Res res, Long id) {
        return new EntityModel<>(res,
                linkTo(CrudController.class).withRel("list"),
                linkTo(CrudController.class).slash(id).withSelfRel());
    }
}