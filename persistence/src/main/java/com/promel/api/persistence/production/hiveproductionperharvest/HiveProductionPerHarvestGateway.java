package com.promel.api.persistence.production.hiveproductionperharvest;

import com.promel.api.domain.model.HiveProductionPerHarvest;
import com.promel.api.usecase.production.hiveproductionperharvest.adapter.HiveProductionPerHarvestAdapter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HiveProductionPerHarvestGateway implements HiveProductionPerHarvestAdapter {

    private HiveProductionPerHarvestRepository repository;
    private ModelMapper modelMapper;

    @Override
    public HiveProductionPerHarvest save(HiveProductionPerHarvest hpph) {
        return toModel(repository.save(toEntity(hpph)));
    }

    @Override
    public boolean associationHasHoneyProductionAndHive(Long honeyProductionId, Long hiveId) {
        return repository.associationHasHoneyProductionAndHive(honeyProductionId, hiveId);
    }

    private HiveProductionPerHarvestEntity toEntity(HiveProductionPerHarvest hpph) {
        return modelMapper.map(hpph, HiveProductionPerHarvestEntity.class);
    }

    private HiveProductionPerHarvest toModel(HiveProductionPerHarvestEntity entity) {
        return modelMapper.map(entity, HiveProductionPerHarvest.class);
    }

}
