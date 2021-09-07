package com.promel.api.persistence.role;

import com.promel.api.usecase.role.adapter.RoleAdapter;
import com.promel.api.domain.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleGateway implements RoleAdapter {

    private RoleRepository repository;
    private ModelMapper modelMapper;

    public RoleGateway(RoleRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.modelMapper = mapper;
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        return repository.findOneByRole(roleName).map(this::toDomain);
    }

    @Override
    public List<Role> findAllByNames(Set<String> roleNames) {
        return repository.findAllByRoleIn(roleNames).stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Role toDomain(RoleEntity entity) {
        return modelMapper.map(entity, Role.class);
    }
}
