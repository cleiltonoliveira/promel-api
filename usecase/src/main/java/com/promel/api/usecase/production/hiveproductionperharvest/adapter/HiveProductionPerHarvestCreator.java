package com.promel.api.usecase.production.hiveproductionperharvest.adapter;

import com.promel.api.domain.model.HiveProductionPerHarvest;
import com.promel.api.usecase.exception.CustomForbiddenException;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionFinder;
import com.promel.api.usecase.user.UserAccountFinder;

import javax.inject.Named;
import java.util.Objects;

@Named
public class HiveProductionPerHarvestCreator {

    private final HiveProductionPerHarvestAdapter adapter;
    private final HoneyProductionFinder honeyProductionFinder;
    private final UserAccountFinder userAccountFinder;

    public HiveProductionPerHarvestCreator(HiveProductionPerHarvestAdapter adapter, HoneyProductionFinder honeyProductionFinder, UserAccountFinder userAccountFinder) {
        this.adapter = adapter;
        this.honeyProductionFinder = honeyProductionFinder;
        this.userAccountFinder = userAccountFinder;
    }

    public HiveProductionPerHarvest create(HiveProductionPerHarvest hpph, String userEmail) {
        var honeyProduction = honeyProductionFinder.findById(hpph.getId().getHoneyProductionId());
        var associationId = userAccountFinder.findByEmail(userEmail).getAssociation().getId();

        if (!Objects.equals(honeyProduction.getAssociationId(), associationId)){
            throw new CustomForbiddenException("Forbidden");
        }

        if (honeyProduction.getEndDate() != null) {
            throw new ResourceConflictException("The harvest is over");
        }
        verifyIdsOwner(hpph.getId().getHiveId(), hpph.getId().getHoneyProductionId());

        return adapter.save(hpph);
    }

    /*
     * Verify if the honey production event and the hive belong to the same association
     * */
    private void verifyIdsOwner(Long honeyProductionId, Long hiveId) {
        if (!adapter.associationHasHoneyProductionAndHive(honeyProductionId, hiveId)) {
            throw new ResourceConflictException("Honey production and the hive do not belong to the same association");
        }
    }
}
