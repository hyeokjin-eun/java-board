package com.board.java.domain.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionApiRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
