package com.promel.api.persistence.hiveproductionperharvest;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "hive_production_per_harvest")
public class HiveProductionPerHarvest {
    @EmbeddedId
    private HiveProductionPerHarvestKey id;

    @Column(name = "number_of_extracted_frames" )
    @NotNull
    private Integer numberOfExtractedFrames;

    @Column(name = "parcial_production", precision = 6, scale = 4)
    @NotNull
    private Double partialProduction;
}