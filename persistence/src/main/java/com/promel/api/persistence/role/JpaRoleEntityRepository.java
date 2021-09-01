package com.promel.api.persistence.role;


import com.promel.api.persistence.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findOneByRole(String role);
}
