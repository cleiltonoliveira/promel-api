package com.promel.api.usecase.authentication;

import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.usecase.exception.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserAuthFinder {
    private UserAuthAdapter userAuthAdapter;

    @Inject
    public UserAuthFinder(UserAuthAdapter userAuthAdapter) {
        this.userAuthAdapter = userAuthAdapter;
    }

    public UserAuth findByEmail(String email) {
        return userAuthAdapter.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No users were found with this email: " + email));
    }
}
