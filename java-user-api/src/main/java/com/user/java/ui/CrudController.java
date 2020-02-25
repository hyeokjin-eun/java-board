package com.user.java.ui;

import com.user.java.application.BaseService;
import com.user.java.domain.ModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public abstract class CrudController<Req, Res, Entity> implements CrudInterface<Req, Res> {

    protected BaseService<Req, Res, Entity> baseService;

    protected ModelAssembler<Res> modelAssembler;

    @Autowired
    public void setBaseService(BaseService<Req, Res, Entity> baseService) {
        this.baseService = baseService;
    }

    @Autowired
    public void setModelAssembler(ModelAssembler<Res> modelAssembler) {
        this.modelAssembler = modelAssembler;
    }

    @Override
    public ResponseEntity<Res> create(final Req req) {
        return null;
    }

    @Override
    @GetMapping("{id}")
    public EntityModel<Res> detail(@PathVariable final Long id) {
        return modelAssembler.toModel(baseService.detail(id));
    }

    @Override
    @GetMapping("")
    public CollectionModel<EntityModel<Res>> list() {
        return modelAssembler.toCollectionModel(baseService.list());
    }
}
