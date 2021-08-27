package com.promei.api.usecase.user.impl;

import com.promei.api.exception.ResourceConflictException;
import com.promei.api.repository.UserAccountRepository;
import com.promei.api.repository.UserAuthRepository;
import com.promei.api.usecase.role.FindRole;
import com.promei.api.usecase.user.CreateUserAccount;
import com.promel.api.model.UserAccount;
import com.promel.api.model.UserAuth;

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
