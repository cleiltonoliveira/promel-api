package com.promel.api.persistence.gateway;

import com.promei.api.repository.RoleRepository;
import com.promel.api.persistence.entity.RoleEntity;
import com.promel.api.model.Role;
import com.promel.api.persistence.repository.JpaRoleEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleGateway implements RoleRepository {

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
