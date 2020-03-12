package com.board.java.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApiRequest {

    @JsonProperty
    @NotBlank
    @Email
    private String email;

    @JsonProperty
    @NotBlank
    private String password;

    @JsonProperty
    @NotBlank
    private String name;
}
