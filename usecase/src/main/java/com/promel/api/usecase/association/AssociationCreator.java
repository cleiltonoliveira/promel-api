
package com.promel.api.usecase.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.ResourceConflictException;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

@Named
public class AssociationCreator {
    private AssociationAdapter associationAdapter;

    @Inject
    public AssociationCreator(AssociationAdapter associationAdapter) {
        this.associationAdapter = associationAdapter;
    }

    public Association create(Association association) {
        verifyIfCnpjExists(association.getCnpj());
        association.setCreationDate(LocalDateTime.now());
        return associationAdapter.save(association);
    }

    private void verifyIfCnpjExists(String cnpj) {
        if (associationAdapter.existsByCnpj(cnpj)) {
            throw new ResourceConflictException("An association is already registered with this cnpj");
        }
    }
}
