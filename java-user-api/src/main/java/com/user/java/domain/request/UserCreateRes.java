package com.user.java.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRes {
    private Long id;
    private String email;
    private String password;
    private String name;
}
