package com.promel.api.persistence.production.honeyproduction;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.production.adapter.HoneyProductionAdapter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public boolean existsByAssociationIdAndEndDate(Long associationId, LocalDateTime endDate) {
        return repository.existsByAssociationIdAndEndDate(associationId, endDate);
    }

    @Override
    public List<HoneyProduction> findAllHoneyProductionByAssociationId(Long associationId) {
        return repository.findAllByAssociationId(associationId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    private HoneyProduction toDomain(HoneyProductionEntity entity) {
        return modelMapper.map(entity, HoneyProduction.class);
    }

    private HoneyProductionEntity toEntity(HoneyProduction domain) {
        return modelMapper.map(domain, HoneyProductionEntity.class);
    }
}
