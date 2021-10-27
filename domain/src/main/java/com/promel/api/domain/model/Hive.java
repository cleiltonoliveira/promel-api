package com.promel.api.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Hive {
    private Long id;
    private String description;
    private String identificationCode;
    private LocalDateTime creationDate;
    private Long productionUnitId;
}
