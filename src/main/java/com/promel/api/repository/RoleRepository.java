package com.promel.api.repository;

import com.promel.api.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query(value = "SELECT * FROM  `role` WHERE `role` = 'USER' LIMIT 1", nativeQuery = true)
    Optional<Role> findUserRole();
    @Query(value = "SELECT * FROM  `role` WHERE `role` = 'ADMIN' LIMIT 1", nativeQuery = true)
    Optional<Role> findAdminRole();
}
