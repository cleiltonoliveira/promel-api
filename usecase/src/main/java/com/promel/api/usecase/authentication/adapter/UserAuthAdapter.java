package com.promel.api.usecase.authentication.adapter;

import com.promel.api.domain.model.UserAuth;

import java.util.Optional;

public interface UserAuthAdapter {

    boolean existsByEmail(String email);

    Optional<UserAuth> findByEmail(String email);
}
