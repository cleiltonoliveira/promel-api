package com.promel.api.usecase.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class AssociationFinder {
    private AssociationAdapter adapter;

    @Inject
    public AssociationFinder(AssociationAdapter adapter) {
        this.adapter = adapter;
    }

    public Association findById(Long id) {
        return adapter.findById(id).orElseThrow(() -> new ResourceNotFoundException("No associations found with the ID provided"));
    }

    public List<String> findAssociationMembers(Long associationId) {
        return adapter.findAssociationMembers(associationId);
    }

    public boolean associationExistsById(Long id) {
        return adapter.existsById(id);
    }

    public boolean associationExistsByInviteCode(String inviteCode) {
        return adapter.existsByInviteCode(inviteCode);
    }

    public Association findByInviteCode(String inviteCode) {
        return adapter.findByInviteCode(inviteCode).orElseThrow(() -> new ResourceNotFoundException("No associations found with the invite code provided"));
    }
}
