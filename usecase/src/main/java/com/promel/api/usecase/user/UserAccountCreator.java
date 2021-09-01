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
        userAccount.getUserAuth().setRole(roleFinder.findRoleByName(RoleType.USER));
        userAccount.setCreationDate(LocalDateTime.now());
        return userAccountAdapter.save(userAccount);
    }

    private void verifyIfEmailExists(UserAuth userAuth) {
        if (userAuthAdapter.existsByEmail(userAuth.getEmail())) {
            ResourceConflictException ex = new ResourceConflictException("An account already exists with this email");
            ex.setError("Email unavailable");
            throw ex;
        }
    }
}
