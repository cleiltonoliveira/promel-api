package com.promel.api.persistence.production.honeyproduction;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "honey_production")
public class HoneyProductionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "harvest_date")
    private LocalDateTime harvestDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull
    @Column(name = "total_production", precision = 12, scale = 4)
    private BigDecimal totalProduction;

    @Column(name = "association_id", updatable = false)
    @NotNull
    private Long associationId;
}
