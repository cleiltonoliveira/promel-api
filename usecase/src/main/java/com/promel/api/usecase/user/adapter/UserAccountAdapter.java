package com.promel.api.usecase.user.adapter;

import com.promel.api.domain.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountAdapter {

    UserAccount save(UserAccount userAccount);

    void deleteById(Long id);

    Optional<UserAccount> findById(Long id);

    List<UserAccount> findAll();

    boolean existsById(Long id);
}