package com.promel.api.usecase.production;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.production.adapter.HoneyProductionAdapter;

import javax.inject.Named;
import java.util.Optional;

@Named
public class HoneyProductionFinder {
    private final HoneyProductionAdapter honeyProductionAdapter;

    public HoneyProductionFinder(HoneyProductionAdapter honeyProductionAdapter) {
        this.honeyProductionAdapter = honeyProductionAdapter;
    }

    public Optional<HoneyProduction> findHoneyProductionInProgress(Long associationId){
       return  honeyProductionAdapter.findByAssociationIdAndEndDate(associationId, null);
    }
}
