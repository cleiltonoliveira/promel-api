package com.promel.api.web.controller.user.dto;

import lombok.Data;

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
