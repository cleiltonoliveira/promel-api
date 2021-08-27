package com.promel.api.gateway;

import com.promel.api.entity.UserAuth;
import com.promel.api.persistence.entity.UserAuthEntity;
import com.promel.api.persistence.repository.JpaUserAuthRepository;
import com.promel.api.repository.UserAuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuthGateway implements UserAuthRepository {

    private JpaUserAuthRepository repository;
    private ModelMapper modelMapper;

    public UserAuthGateway(JpaUserAuthRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    private UserAuth toDomain(UserAuthEntity entity) {
        return modelMapper.map(entity, UserAuth.class);
    }
}
