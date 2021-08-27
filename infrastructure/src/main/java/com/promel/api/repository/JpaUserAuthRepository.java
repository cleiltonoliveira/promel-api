package com.promel.api.repository;

import com.promel.api.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserAuthEntity> findByEmail(String email);
}
