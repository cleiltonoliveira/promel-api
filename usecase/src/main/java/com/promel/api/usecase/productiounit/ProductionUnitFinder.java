package com.promel.api.usecase.productiounit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.productiounit.adapter.ProductionUnitAdapter;
import com.promel.api.usecase.user.UserAccountFinder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProductionUnitFinder {

    private ProductionUnitAdapter adapter;
    private UserAccountFinder userAccountFinder;

    @Inject
    public ProductionUnitFinder(ProductionUnitAdapter adapter, UserAccountFinder userAccountFinder) {
        this.adapter = adapter;
        this.userAccountFinder = userAccountFinder;
    }

    public ProductionUnit findById(Long id) {
        return adapter.findById(id).orElseThrow(() -> new ResourceNotFoundException("Production unit not found"));
    }

    public ProductionUnit findByUser(String email) {
        return adapter.findFirstByUserId(userAccountFinder.findByEmail(email).getId()).orElseThrow(() -> new ResourceNotFoundException("Production unit not found"));
    }
}
