package com.user.java.ui;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface CrudInterface<Req, Res> {
    ResponseEntity<Res> create(final Req req);

    EntityModel<Res> detail(final Long id);

    CollectionModel<EntityModel<Res>> list();
}
