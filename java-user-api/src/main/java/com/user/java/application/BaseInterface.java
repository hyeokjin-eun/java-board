package com.user.java.application;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface BaseInterface<Req, Res> {
    EntityModel<Res> create(final Req req);

    EntityModel<Res> detail(final Long id);

    CollectionModel<EntityModel<Res>> list();
}
