package com.promel.api.usecase.role;

import com.promel.api.usecase.exception.InternalErrorException;
import com.promel.api.usecase.role.adapter.RoleAdapter;
import com.promel.api.domain.model.Role;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RoleFinder{

    private RoleAdapter roleAdapter;

    @Inject
    public RoleFinder(RoleAdapter roleAdapter) {
        this.roleAdapter = roleAdapter;
    }

    public Role findUserRole() {
        Role role = roleAdapter.findUserRole()
                .orElseThrow(() -> new InternalErrorException("There was an unexpected error"));
        return role;
    }

    public Role findAdminRole() {
        Role role = roleAdapter.findAdminRole()
                .orElseThrow(() -> new InternalErrorException("There was an unexpected error"));
        return role;
    }
}
