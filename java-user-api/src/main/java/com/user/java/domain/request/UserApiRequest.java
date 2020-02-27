package com.user.java.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApiRequest {

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private String name;
}
