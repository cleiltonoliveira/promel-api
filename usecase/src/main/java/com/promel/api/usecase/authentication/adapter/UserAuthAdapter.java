package com.promel.api.usecase.authentication.adapter;

import com.promel.api.domain.model.UserAuth;

import java.util.Optional;

public interface UserAuthAdapter {

    UserAuth save(UserAuth userAuth);

    boolean existsByEmail(String email);

    Optional<UserAuth> findByEmail(String email);
}
