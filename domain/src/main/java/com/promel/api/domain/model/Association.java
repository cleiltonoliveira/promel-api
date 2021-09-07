package com.promel.api.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Association {
    private Long id;
    private String name;
    private String cnpj;
    private String inviteCode;
    private LocalDateTime creationDate;
}

