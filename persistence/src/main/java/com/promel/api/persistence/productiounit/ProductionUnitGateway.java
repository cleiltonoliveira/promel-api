package com.promel.api.persistence.productiounit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.usecase.productiounit.adapter.ProductionUnitAdapter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductionUnitGateway implements ProductionUnitAdapter {

    private ProductionUnitRepository repository;
    private ModelMapper modelMapper;

    @Override
    public Optional<ProductionUnit> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public ProductionUnit save(ProductionUnit productionUnit) {
        return toDomain(repository.save(toEntity(productionUnit)));
    }

    @Override
    public Optional<ProductionUnit> findFirstByUserId(Long id) {
        return repository.findFirstByUserAccountId(id).map(this::toDomain);
    }

    private ProductionUnit toDomain(ProductionUnitEntity entity) {
        return modelMapper.map(entity, ProductionUnit.class);
    }

    private ProductionUnitEntity toEntity(ProductionUnit domain) {
        return modelMapper.map(domain, ProductionUnitEntity.class);
    }
}
