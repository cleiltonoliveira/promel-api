package com.promel.api.usecase.production;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.production.adapter.HoneyProductionAdapter;
import com.promel.api.usecase.user.UserAccountFinder;

import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Named
public class HoneyProductionCreator {
    private final HoneyProductionAdapter adapter;
    private final UserAccountFinder userAccountFinder;
    private final HoneyProductionFinder honeyProductionFinder;

    public HoneyProductionCreator(HoneyProductionAdapter adapter, UserAccountFinder userAccountFinder, HoneyProductionFinder honeyProductionFinder) {
        this.adapter = adapter;
        this.userAccountFinder = userAccountFinder;
        this.honeyProductionFinder = honeyProductionFinder;
    }

    public HoneyProduction create(String userEmail) {
        var userAccount = userAccountFinder.findByEmail(userEmail);

        if (userAccount.getAssociation() != null) {
            verifyIfAssociationHasHoneyProductionInProgress(userAccount.getAssociation().getId());
        } else {
            throw new ResourceNotFoundException("Association not found");
        }

        var honeyProduction = new HoneyProduction();
        honeyProduction.setTotalProduction(BigDecimal.valueOf(0));
        honeyProduction.setHarvestDate(LocalDateTime.now());
        honeyProduction.setAssociationId(userAccount.getAssociation().getId());

        return adapter.save(honeyProduction);
    }

    /*
     *   It is not possible to start a new honey production if one is already in progress.
     *   Therefore, we need to check this use case.
     * */
    public void verifyIfAssociationHasHoneyProductionInProgress(Long associationId) {
        if (honeyProductionFinder.existsHoneyProductionInProgress(associationId)) {
            throw new ResourceConflictException("There is already a honey production in progress");
        }
    }
}
