package com.promel.api.persistence.authentication;

import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import com.promel.api.domain.model.UserAuth;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuthGateway implements UserAuthAdapter {

    private UserAuthRepository repository;
    private ModelMapper modelMapper;

    public UserAuthGateway(UserAuthRepository repository, ModelMapper modelMapper) {
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
