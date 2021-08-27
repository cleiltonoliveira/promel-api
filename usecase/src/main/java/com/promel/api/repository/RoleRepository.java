package com.promel.api.repository;

import com.promel.api.model.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findUserRole();

    Optional<Role> findAdminRole();
}
