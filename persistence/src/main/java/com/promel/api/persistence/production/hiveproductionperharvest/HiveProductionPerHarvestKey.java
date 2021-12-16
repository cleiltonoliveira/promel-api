package com.promel.api.persistence.production.hiveproductionperharvest;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class HiveProductionPerHarvestKey implements Serializable {
    @Column(name = "honey_production_id")
    private Long honeyProductionId;

    @Column(name = "hive_id")
    private Long hiveId;
}
