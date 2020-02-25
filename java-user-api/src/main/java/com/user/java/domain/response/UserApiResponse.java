package com.user.java.domain.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApiResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
}
