package com.promel.api.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductionUnit {
    private Long id;
    private Double totalProduction;
    private LocalDateTime lastModificationDate;
    private Long userAccountId;
}
