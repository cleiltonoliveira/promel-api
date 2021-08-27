package com.promel.api.repository;


import com.promel.api.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = "SELECT * FROM  `role` WHERE `role` = 'USER' LIMIT 1", nativeQuery = true)
    Optional<RoleEntity> findUserRole();
    @Query(value = "SELECT * FROM  `role` WHERE `role` = 'ADMIN' LIMIT 1", nativeQuery = true)
    Optional<RoleEntity> findAdminRole();
}
