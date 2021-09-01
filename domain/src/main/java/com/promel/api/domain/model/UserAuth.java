package com.promel.api.domain.model;

import lombok.Data;

@Data
public class UserAuth {
    private Long id;
    private String password;
    private String email;
    private Role role;
}
