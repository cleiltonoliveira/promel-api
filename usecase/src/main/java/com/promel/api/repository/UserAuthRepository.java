package com.promel.api.repository;

import com.promel.api.domain.model.UserAuth;

import java.util.Optional;

public interface UserAuthRepository {

    boolean existsByEmail(String email);

    Optional<UserAuth> findByEmail(String email);
}
