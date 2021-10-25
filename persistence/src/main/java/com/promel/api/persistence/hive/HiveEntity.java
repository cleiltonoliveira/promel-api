package com.promel.api.persistence.hive;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "hive")
public class HiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "identification_code")
    private String identificationCode;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "production_unit_id", updatable = false)
    private Long productionUnitId;
}