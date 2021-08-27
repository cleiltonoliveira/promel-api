package com.promel.api.dto.transport.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserAuthInput {

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;
}
