package com.promel.api.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HoneyProduction {
    private Long id;
    private LocalDateTime harvestDate;
    private LocalDateTime endDate;
    private BigDecimal totalProduction;
    private Long associationId;
}
