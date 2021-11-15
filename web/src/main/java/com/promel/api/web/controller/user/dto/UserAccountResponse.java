package com.promel.api.web.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserAccountResponse {
    private String name;
    private LocalDateTime creationDate;
    private String phone;
    @JsonProperty("email")
    private String userAuthEmail;
    private Long associationId;
    private List<String> roles;
}
