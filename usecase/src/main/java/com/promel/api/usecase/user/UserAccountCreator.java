package com.promel.api.usecase.user;

import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.role.RoleFinder;
import com.promel.api.usecase.role.RoleType;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.HashSet;

@Named
public class UserAccountCreator {
    private UserAccountAdapter userAccountAdapter;
    private UserAuthAdapter userAuthAdapter;
    private RoleFinder roleFinder;

    @Inject
    public UserAccountCreator(UserAccountAdapter userAccountAdapter, UserAuthAdapter userAuthAdapter, RoleFinder roleFinder) {
        this.userAccountAdapter = userAccountAdapter;
        this.userAuthAdapter = userAuthAdapter;
        this.roleFinder = roleFinder;
    }

    public UserAccount create(UserAccount userAccount) {
        verifyIfEmailExists(userAccount.getUserAuth());

        var roles = new HashSet<String>();
        roles.add(RoleType.USER.name());

        userAccount.getUserAuth().setRoles(roleFinder.findAllByRoleNames(roles));
        userAccount.setCreationDate(LocalDateTime.now());
        return userAccountAdapter.save(userAccount);
    }

    private void verifyIfEmailExists(UserAuth userAuth) {
        if (userAuthAdapter.existsByEmail(userAuth.getEmail())) {
            throw new ResourceConflictException("An account already exists with this email");
        }
    }
}
