package com.promel.api.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findOneByRole(String role);

    List<RoleEntity> findAllByRoleIn(Set<String> roles);
}
