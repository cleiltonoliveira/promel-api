package com.promel.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HiveProductionPerHarvestKey {
    private Long honeyProductionId;
    private Long hiveId;
}
