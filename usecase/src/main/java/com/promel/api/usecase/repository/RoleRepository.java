package com.promel.api.usecase.repository;

import com.promel.api.domain.model.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findUserRole();

    Optional<Role> findAdminRole();
}