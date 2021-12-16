package com.promel.api.persistence.production.honeyproduction;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.production.adapter.HoneyProductionAdapter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class HoneyProductionGateway implements HoneyProductionAdapter {

    private HoneyProductionRepository repository;
    private ModelMapper modelMapper;

    @Override
    public HoneyProduction save(HoneyProduction model) {
        return toDomain(repository.save(toEntity(model)));
    }

    @Override
    public Optional<HoneyProduction> findByAssociationIdAndEndDate(Long associationId, LocalDateTime endDate) {
        return repository.findByAssociationIdAndEndDate(associationId, endDate).map(this::toDomain);
    }

    private HoneyProduction toDomain(HoneyProductionEntity entity) {
        return modelMapper.map(entity, HoneyProduction.class);
    }

    private HoneyProductionEntity toEntity(HoneyProduction domain) {
        return modelMapper.map(domain, HoneyProductionEntity.class);
    }
}
