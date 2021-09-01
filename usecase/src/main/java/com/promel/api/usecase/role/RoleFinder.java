package com.promel.api.usecase.role;

import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.role.adapter.RoleAdapter;
import com.promel.api.domain.model.Role;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RoleFinder {

    private RoleAdapter roleAdapter;

    @Inject
    public RoleFinder(RoleAdapter roleAdapter) {
        this.roleAdapter = roleAdapter;
    }

    public Role findRoleByName(RoleType roleType) {
        return roleAdapter.findRoleByName(roleType.name())
                .orElseThrow(() -> new ResourceNotFoundException("No role found for provided name: " + roleType.name()));
    }
}
