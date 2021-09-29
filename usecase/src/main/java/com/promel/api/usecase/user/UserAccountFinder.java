package com.promel.api.usecase.user;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class UserAccountFinder {
    private UserAccountAdapter userAccountAdapter;

    @Inject
    public UserAccountFinder(UserAccountAdapter userAccountAdapter) {
        this.userAccountAdapter = userAccountAdapter;
    }

    public UserAccount findById(Long id) {
        return userAccountAdapter.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No users found with the ID provided"));
    }

    public List<UserAccount> findAll() {
        return userAccountAdapter.findAll();
    }

    public UserAccount findByUserAuth(UserAuth userAuth) {
        return userAccountAdapter.findByUserAuth(userAuth)
                .orElseThrow(() -> new ResourceNotFoundException("No users found with the given authentication"));
    }
}
