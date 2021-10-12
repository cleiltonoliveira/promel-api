package com.promel.api.web.controller.association.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssociationResponse {
    private Long id;
    private String name;
    private String cnpj;
    private String inviteCode;
    private LocalDateTime creationDate;
}
