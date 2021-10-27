package com.promel.api.persistence.hive;

import com.promel.api.domain.model.Hive;
import com.promel.api.usecase.hive.adapter.HiveAdapter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HiveGateway implements HiveAdapter {

    private HiveRepository repository;
    private ModelMapper modelMapper;

    @Override
    public Hive save(Hive hive) {
        return toDomain(repository.save(toEntity(hive)));
    }

    @Override
    public boolean existsByIdentificationCodeAndProductionUnitId(String idCode, Long unitId) {
        return repository.existsByIdentificationCodeAndProductionUnitId(idCode, unitId);
    }

    private Hive toDomain(HiveEntity entity) {
        return modelMapper.map(entity, Hive.class);
    }

    private HiveEntity toEntity(Hive domain) {
        return modelMapper.map(domain, HiveEntity.class);
    }

}
