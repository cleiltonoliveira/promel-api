package com.promel.api.usecase.role.impl;

import com.promel.api.exception.InternalErrorException;
import com.promel.api.repository.RoleRepository;
import com.promel.api.usecase.role.FindRole;
import com.promel.api.model.Role;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class FindRoleImpl implements FindRole {

    private RoleRepository roleRepository;

    @Inject
    public FindRoleImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findUserRole() {
        Role role = roleRepository.findUserRole()
                .orElseThrow(() -> new InternalErrorException("There was an unexpected error"));
        return role;
    }

    @Override
    public Role findAdminRole() {
        Role role = roleRepository.findAdminRole()
                .orElseThrow(() -> new InternalErrorException("There was an unexpected error"));
        return role;
    }
}
