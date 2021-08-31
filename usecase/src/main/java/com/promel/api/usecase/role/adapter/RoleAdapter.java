package com.promel.api.usecase.role.adapter;

import com.promel.api.domain.model.Role;

import java.util.Optional;

public interface RoleAdapter {

    Optional<Role> findUserRole();

    Optional<Role> findAdminRole();
}
