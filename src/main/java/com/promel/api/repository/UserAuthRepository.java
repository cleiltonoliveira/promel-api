package com.promel.api.repository;

import com.promel.api.model.UserAuth;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAuthRepository extends CrudRepository<UserAuth, Long> {

    boolean existsByEmail(String email);

    Optional<UserAuth> findByEmail(String email);
}
