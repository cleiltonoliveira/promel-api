package com.promel.api.usecase.role.adapter;

import com.promel.api.domain.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface RoleAdapter {

    Optional<Role> findRoleByName(String roleName);

    List<Role> findAllByNames(HashSet<String> roleNames);
}
