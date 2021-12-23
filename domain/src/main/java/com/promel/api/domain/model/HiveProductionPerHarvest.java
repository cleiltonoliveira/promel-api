package com.promel.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HiveProductionPerHarvest {
    private HiveProductionPerHarvestKey id;
    private Integer numberOfExtractedFrames;
    private Double partialProduction;
}