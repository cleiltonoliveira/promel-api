package com.promel.api.repository;

import com.promel.api.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

}
