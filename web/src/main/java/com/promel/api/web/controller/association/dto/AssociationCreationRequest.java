package com.promel.api.web.controller.association.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AssociationCreationRequest {

    @NotBlank
    private String name;

    @NotBlank
    @CNPJ
    private String cnpj;

    @NotNull
    private Long ownId;
}
