package com.promel.api.persistence.role;

import com.promel.api.usecase.role.adapter.RoleAdapter;
import com.promel.api.domain.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleGateway implements RoleAdapter {

    private JpaRoleEntityRepository repository;
    private ModelMapper modelMapper;


    public RoleGateway(JpaRoleEntityRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.modelMapper = mapper;
    }

    @Override
    public Optional<Role> findUserRole() {
        return repository.findUserRole().map(this::toDomain);
    }

    @Override
    public Optional<Role> findAdminRole() {
        return repository.findAdminRole().map(this::toDomain);
    }

    private Role toDomain(RoleEntity entity) {
        return modelMapper.map(entity, Role.class);
    }
}
