package com.promel.api.usecase.productiounit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.usecase.productiounit.adapter.ProductionUnitAdapter;
import com.promel.api.usecase.user.UserAccountFinder;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

@Named
public class ProductionUnitCreator {

    private ProductionUnitAdapter adapter;
    private UserAccountFinder userAccountFinder;

    @Inject
    public ProductionUnitCreator(ProductionUnitAdapter adapter, UserAccountFinder userAccountFinder) {
        this.adapter = adapter;
        this.userAccountFinder = userAccountFinder;
    }

    public ProductionUnit create(String userEmail) {
        var userId = userAccountFinder.findByEmail(userEmail).getId();
        var model = new ProductionUnit();

        model.setUserAccountId(userId);
        model.setLastModificationDate(LocalDateTime.now());
        model.setTotalProduction(0.0);
        return adapter.save(model);
    }
}
