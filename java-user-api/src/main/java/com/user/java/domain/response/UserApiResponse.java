package com.user.java.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApiResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private String name;
}
