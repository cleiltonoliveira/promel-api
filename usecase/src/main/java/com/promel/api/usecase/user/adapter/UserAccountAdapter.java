package com.promel.api.usecase.user.adapter;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;

import java.util.List;
import java.util.Optional;

public interface UserAccountAdapter {

    UserAccount save(UserAccount userAccount);

    void deleteById(Long id);

    Optional<UserAccount> findById(Long id);

    List<UserAccount> findAll();

    Optional<UserAccount>  findByUserAuth(UserAuth userAuth);

    Optional<UserAccount>  findByEmail(String email);

    boolean existsById(Long id);
}
