package com.promel.api.web.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccountResponse {
    private String name;
    private LocalDateTime creationDate;
    private String phone;
    @JsonProperty("email")
    private String userAuthEmail;
}
