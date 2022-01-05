package com.promel.api.usecase.production.hiveproductionperharvest.adapter;

import com.promel.api.domain.model.HiveProductionPerHarvest;

public interface HiveProductionPerHarvestAdapter {
    HiveProductionPerHarvest save(HiveProductionPerHarvest hpph);

    boolean associationHasHoneyProductionAndHive(Long honeyProductionId, Long hiveId);
}
