package com.user.java.domain.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateReq {
    private String email;
    private String password;
    private String name;
}
