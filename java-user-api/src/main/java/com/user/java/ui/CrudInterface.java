package com.user.java.ui;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface CrudInterface<Req, Res> {
    ResponseEntity<EntityModel<Res>> create(final Req req) throws URISyntaxException;

    ResponseEntity<EntityModel<Res>> detail(final Long id);

    ResponseEntity<CollectionModel<EntityModel<Res>>> list();
}
