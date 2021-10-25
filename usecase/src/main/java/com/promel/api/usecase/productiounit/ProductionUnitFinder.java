package com.promel.api.usecase.productiounit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.productiounit.adapter.ProductionUnitAdapter;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProductionUnitFinder {

    private ProductionUnitAdapter adapter;

    @Inject
    public ProductionUnitFinder(ProductionUnitAdapter adapter) {
        this.adapter = adapter;
    }

    public ProductionUnit findById(Long id) {
        return adapter.findById(id).orElseThrow(() -> new ResourceNotFoundException("Production unit not found"));
    }
}
