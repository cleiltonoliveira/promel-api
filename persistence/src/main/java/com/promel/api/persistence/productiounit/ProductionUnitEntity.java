package com.promel.api.persistence.productiounit;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "production_unit")
public class ProductionUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "total_production")
    @NotNull
    private Double totalProduction;

    @Column(name = "last_modification_date", updatable = false)
    @NotNull
    private LocalDateTime lastModificationDate;

    @Column(name = "user_account_id", updatable = false)
    @NotNull
    private Long userAccountId;
}
