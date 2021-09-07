package com.promel.api.web.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserAccountCreationRequest {
    @NotBlank
    private String name;

    private String phone;

    @NotBlank
    @JsonAlias({"password"})
    private String userAuthPassword;

    @NotBlank
    @Email
    @JsonAlias({"email"})
    private String UserAuthEmail;
}