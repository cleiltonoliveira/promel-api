package com.promel.api.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class UserAuth {
    private Long id;
    private String password;
    private String email;
    private List<Role> roles;
}
