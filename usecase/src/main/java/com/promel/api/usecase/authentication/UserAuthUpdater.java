package com.promel.api.usecase.authentication;

import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.usecase.role.RoleFinder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class UserAuthUpdater {
    private UserAuthAdapter userAuthAdapter;
    private UserAuthFinder userAuthFinder;
    private RoleFinder roleFinder;

    @Inject
    public UserAuthUpdater(UserAuthAdapter userAuthAdapter, UserAuthFinder userAuthFinder, RoleFinder roleFinder) {
        this.userAuthAdapter = userAuthAdapter;
        this.userAuthFinder = userAuthFinder;
        this.roleFinder = roleFinder;
    }

    public UserAuth updateRoles(String email, Set<String> newRoles) {
        var userAuth = userAuthFinder.findByEmail(email);

        userAuth.setRoles(roleFinder.findAllByRoleNames(newRoles));
       return userAuthAdapter.save(userAuth);
    }
}
