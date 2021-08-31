package com.promel.api.usecase.user.impl;

import com.promel.api.usecase.exception.ResourceConflictException;
import com.promel.api.usecase.repository.UserAccountRepository;
import com.promel.api.usecase.repository.UserAuthRepository;
import com.promel.api.usecase.role.FindRole;
import com.promel.api.usecase.user.CreateUserAccount;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

@Named
public class CreateUserAccountImpl implements CreateUserAccount {
    private UserAccountRepository userAccountRepository;
    private UserAuthRepository userAuthRepository;
    private FindRole findRole;

    @Inject
    public CreateUserAccountImpl(UserAccountRepository userAccountRepository, UserAuthRepository userAuthRepository, FindRole findRole) {
        this.userAccountRepository = userAccountRepository;
        this.userAuthRepository = userAuthRepository;
        this.findRole = findRole;
    }

    @Override
    public UserAccount execute(UserAccount userAccount) {
        verifyIfEmailExists(userAccount.getUserAuth());
        userAccount.getUserAuth().setRole(findRole.findUserRole());
        userAccount.setCreationDate(LocalDateTime.now());
        return userAccountRepository.save(userAccount);
    }

    private void verifyIfEmailExists(UserAuth userAuth) {
        if (userAuthRepository.existsByEmail(userAuth.getEmail())) {
            ResourceConflictException ex = new ResourceConflictException("An account already exists with this email");
            ex.setError("Email unavailable");
            throw ex;
        }
    }
}
