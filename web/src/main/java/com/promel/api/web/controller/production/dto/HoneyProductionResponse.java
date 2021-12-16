package com.promel.api.web.controller.production.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HoneyProductionResponse {
    private Long id;
    private LocalDateTime harvestDate;
    private LocalDateTime endDate;
    private BigDecimal totalProduction;
    private Long associationId;
}
