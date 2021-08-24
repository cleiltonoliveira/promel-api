package com.promel.api.service;

import com.promel.api.error.InternalErrorException;
import com.promel.api.model.Role;
import com.promel.api.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    private RoleService(RoleRepository repository) {
        this.roleRepository = repository;
    }

    public Role getUserRole() {
        Role role = roleRepository.findUserRole()
                .orElseThrow(() -> new InternalErrorException("There was an unexpected error"));
        return role;
    }
}
