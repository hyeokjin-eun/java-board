package com.user.java.application;

import com.user.java.domain.response.UserApiResponse;

import java.util.List;

public interface BaseInterface<Req, Res> {
    Res create(final Req req);

    Res detail(final Long id);

    List<UserApiResponse> list();
}
