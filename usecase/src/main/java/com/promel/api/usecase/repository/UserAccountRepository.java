package com.promel.api.usecase.repository;

import com.promel.api.domain.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository {

    UserAccount save(UserAccount userAccount);

    void deleteById(Long id);

    Optional<UserAccount> findById(Long id);

    List<UserAccount> findAll();

    boolean existsById(Long id);
}
