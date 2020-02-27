package com.user.java.ui;

import com.user.java.application.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;

@Component
public abstract class CrudController<Req, Res, Entity> implements CrudInterface<Req, Res> {

    protected BaseService<Req, Res, Entity> baseService;

    @Autowired
    public void setBaseService(BaseService<Req, Res, Entity> baseService) {
        this.baseService = baseService;
    }

    @Override
    @PostMapping("")
    public ResponseEntity<EntityModel<Res>> create(@RequestBody Req req) throws URISyntaxException {
        EntityModel<Res> entityModel = baseService.create(req);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Res>> detail(@PathVariable final Long id) {
        return ResponseEntity.ok()
                .body(baseService.detail(id));
    }

    @Override
    @GetMapping("")
    public ResponseEntity<CollectionModel<EntityModel<Res>>> list() {
        return ResponseEntity.ok()
                .body(baseService.list());
    }
}
