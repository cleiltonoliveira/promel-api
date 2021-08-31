package com.promel.api.persistence.authentication;

import com.promel.api.persistence.user.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

}
