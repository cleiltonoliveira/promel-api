package com.promel.api.web.controller.productionunit.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductionUnitResponse {
    private Long id;
    private Double totalProduction;
    private LocalDateTime lastModificationDate;
    private Long userAccountId;
}
