package com.promel.api.usecase.role.adapter;

import com.promel.api.domain.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleAdapter {

    Optional<Role> findRoleByName(String roleName);

    List<Role> findAllByNames(Set<String> roleNames);
}
