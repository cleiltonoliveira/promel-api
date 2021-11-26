package com.promel.api.usecase.productiounit.adapter;

import com.promel.api.domain.model.ProductionUnit;

import java.util.Optional;

public interface ProductionUnitAdapter {

    Optional<ProductionUnit> findById(Long id);

    ProductionUnit save(ProductionUnit model);

    Optional<ProductionUnit> findFirstByUserId(Long id);
}
