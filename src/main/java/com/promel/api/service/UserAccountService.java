package com.promel.api.service;

import com.promel.api.error.ResourceConflictException;
import com.promel.api.model.UserAccount;
import com.promel.api.model.UserAuth;
import com.promel.api.repository.UserAccountRepository;
import com.promel.api.repository.UserAuthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserAccountService {
    private UserAccountRepository userAccountRepository;
    private UserAuthRepository userAuthRepository;
    private RoleService roleService;

    public UserAccountService(UserAccountRepository userAccountRepository, UserAuthRepository userAuthRepository, RoleService roleService) {
        this.userAccountRepository = userAccountRepository;
        this.userAuthRepository = userAuthRepository;
        this.roleService = roleService;
    }

    public UserAccount save(UserAccount userAccount) {
        verifyIfEmailExists(userAccount.getUserAuth());
        userAccount.getUserAuth().setRole(roleService.getUserRole());
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
