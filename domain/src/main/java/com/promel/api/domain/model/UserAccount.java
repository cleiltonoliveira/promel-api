package com.promel.api.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccount {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private String phone;
    private Association association;
    private UserAuth userAuth;
}
