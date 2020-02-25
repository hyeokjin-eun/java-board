package com.user.java.ui.api;

import com.user.java.domain.request.UserApiRequest;
import com.user.java.domain.response.UserApiResponse;
import com.user.java.infra.User;
import com.user.java.ui.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController extends CrudController<UserApiRequest, UserApiResponse, User> {
}
