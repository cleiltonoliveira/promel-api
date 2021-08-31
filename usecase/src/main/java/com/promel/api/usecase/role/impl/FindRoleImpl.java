package com.promel.api.usecase.role.impl;

import com.promel.api.usecase.exception.InternalErrorException;
import com.promel.api.usecase.role.adapter.RoleAdapter;
import com.promel.api.usecase.role.FindRole;
import com.promel.api.domain.model.Role;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class FindRoleImpl implements FindRole {

    private RoleAdapter roleRepository;

    @Inject
    public FindRoleImpl(RoleAdapter roleRepository) {
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
