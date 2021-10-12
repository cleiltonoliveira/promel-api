package com.promel.api.usecase.user;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.authentication.UserAuthUpdater;
import com.promel.api.usecase.role.RoleType;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class UserAccountUpdater {

    private UserAccountAdapter userAccountAdapter;
    private AssociationFinder associationFinder;
    private UserAccountFinder userAccountFinder;
    private UserAuthUpdater userAuthUpdater;

    @Inject
    public UserAccountUpdater(UserAccountAdapter userAccountAdapter,
                              AssociationFinder associationFinder,
                              UserAccountFinder userAccountFinder,
                              UserAuthUpdater userAuthUpdater) {
        this.userAccountAdapter = userAccountAdapter;
        this.associationFinder = associationFinder;
        this.userAccountFinder = userAccountFinder;
        this.userAuthUpdater = userAuthUpdater;
    }

    @Transactional
    public UserAccount linkUserToAssociation(Long associationId, String userEmail) {
        var userAuth = userAuthUpdater.updateRoles(userEmail, Set.of(RoleType.ASSOCIATION_ADMIN.name(), RoleType.ASSOCIATION_USER.name()));
        var userAccount = userAccountFinder.findByUserAuth(userAuth);
        var association = associationFinder.findById(associationId);

        userAccount.setAssociation(association);

        return userAccountAdapter.save(userAccount);
    }
}
