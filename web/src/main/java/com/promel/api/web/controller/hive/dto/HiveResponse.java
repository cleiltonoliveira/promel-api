package com.promel.api.web.controller.hive.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HiveResponse {
    private Long id;
    private String description;
    private String identificationCode;
    private LocalDateTime creationDate;
    private Long productionUnitId;
}
