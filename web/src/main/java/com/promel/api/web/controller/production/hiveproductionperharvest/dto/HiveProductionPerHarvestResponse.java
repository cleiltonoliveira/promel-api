package com.promel.api.web.controller.production.hiveproductionperharvest.dto;

import lombok.Data;

@Data
public class HiveProductionPerHarvestResponse {
    private Long honeyProductionId;
    private Long hiveId;
    private Integer numberOfExtractedFrames;
    private Double partialProduction;
}
