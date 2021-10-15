package com.promel.api.usecase.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import com.promel.api.usecase.exception.CustomForbiddenException;
import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.user.UserAccountFinder;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@Named
public class AssociationUpdater {
    private AssociationAdapter adapter;
    private AssociationFinder associationFinder;
    private UserAccountFinder userAccountFinder;

    @Inject
    public AssociationUpdater(AssociationAdapter adapter, AssociationFinder associationFinder, UserAccountFinder userAccountFinder) {
        this.adapter = adapter;
        this.associationFinder = associationFinder;
        this.userAccountFinder = userAccountFinder;
    }

    @Transactional
    public Association updateInviteCode(String inviteCode, Long associationId, String userEmail) {
        var user = userAccountFinder.findByEmail(userEmail);

        if (user.getAssociation() == null || !Objects.equals(user.getAssociation().getId(), associationId)) {
            throw new CustomForbiddenException("Forbidden");
        }

        var association = associationFinder.findById(associationId);
        if (association.getInviteCode() != null && association.getInviteCode().equals(inviteCode)) {
            return association;
        }

        verifyIfInviteCodeExists(inviteCode);

        association.setInviteCode(inviteCode);
        return adapter.save(association);
    }

    private void verifyIfInviteCodeExists(String code) {
        if (associationFinder.associationExistsByInviteCode(code)) {
            throw new ResourceConflictException("Invite code unavailable");
        }
    }
}