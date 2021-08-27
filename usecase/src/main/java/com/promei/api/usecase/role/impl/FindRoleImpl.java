package com.promei.api.usecase.role.impl;

import com.promei.api.exception.InternalErrorException;
import com.promei.api.repository.RoleRepository;
import com.promei.api.usecase.role.FindRole;
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
