package com.promel.api.web.controller.hive.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class HiveCreationRequest {
    @NotBlank
    private String identificationCode;
    private String description;
    @NotNull
    private Long productionUnitId;
}
