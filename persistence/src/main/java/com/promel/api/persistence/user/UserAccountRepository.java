package com.promel.api.persistence.user;

import com.promel.api.persistence.authentication.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    Optional<UserAccountEntity> findByUserAuth(UserAuthEntity userAuthEntity);
}