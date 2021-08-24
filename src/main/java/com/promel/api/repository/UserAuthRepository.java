package com.promel.api.repository;

import com.promel.api.model.UserAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends CrudRepository<UserAuth, Long> {

    boolean existsByEmail(String email);

    Optional<UserAuth> findByEmail(String email);
}
