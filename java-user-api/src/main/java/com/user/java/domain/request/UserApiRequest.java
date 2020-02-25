package com.user.java.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApiRequest {
    private String email;
    private String password;
    private String name;
}
