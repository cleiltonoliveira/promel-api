package com.promel.api.usecase.role;

import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.role.adapter.RoleAdapter;
import com.promel.api.domain.model.Role;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class RoleFinder {

    private RoleAdapter roleAdapter;

    @Inject
    public RoleFinder(RoleAdapter roleAdapter) {
        this.roleAdapter = roleAdapter;
    }

    public Role findRoleByName(String name) {
        return roleAdapter.findRoleByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No role found for provided name: " + name));
    }

    public List<Role> findAllByRoleNames(HashSet<String> names) {
        var roles = roleAdapter.findAllByNames(names);

        if (roles.size() < names.size()) {
            var returnedRoleNames = roles.stream().map(Role::getRole).collect(Collectors.toList());
            var missingRoles = names.stream().filter(name -> !returnedRoleNames.contains(name)).collect(Collectors.toList());
            throw new ResourceNotFoundException("The provided roles were not found: " + missingRoles);
        }
        return roles;
    }
}
