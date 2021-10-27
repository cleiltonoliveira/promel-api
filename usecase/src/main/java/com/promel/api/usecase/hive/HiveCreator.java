package com.promel.api.usecase.hive;

import com.promel.api.domain.model.Hive;
import com.promel.api.usecase.exception.CustomForbiddenException;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.hive.adapter.HiveAdapter;
import com.promel.api.usecase.productiounit.ProductionUnitFinder;
import com.promel.api.usecase.user.UserAccountFinder;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.Objects;

@Named
public class HiveCreator {

    private ProductionUnitFinder productionUnitFinder;
    private HiveAdapter hiveAdapter;
    private UserAccountFinder userAccountFinder;

    @Inject
    public HiveCreator(ProductionUnitFinder productionUnitFinder, HiveAdapter hiveAdapter, UserAccountFinder userAccountFinder) {
        this.productionUnitFinder = productionUnitFinder;
        this.hiveAdapter = hiveAdapter;
        this.userAccountFinder = userAccountFinder;
    }

    public Hive create(Hive hive, String userEmail) {
        var userId = userAccountFinder.findByEmail(userEmail).getId();
        var productionUnit = productionUnitFinder.findById(hive.getProductionUnitId());

        if (!Objects.equals(userId, productionUnit.getUserAccountId())) {
            throw new CustomForbiddenException("Forbidden");
        }

        verifyIfHiveExists(hive.getIdentificationCode(), productionUnit.getId());

        hive.setCreationDate(LocalDateTime.now());
        return hiveAdapter.save(hive);
    }

    private void verifyIfHiveExists(String identificationCode, Long unitId) {
        if (hiveAdapter.existsByIdentificationCodeAndProductionUnitId(identificationCode, unitId)) {
            throw new ResourceConflictException("A hive already exists with the provided identification code");
        }
    }
}
