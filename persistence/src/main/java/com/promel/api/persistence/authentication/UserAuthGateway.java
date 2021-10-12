package com.promel.api.persistence.authentication;

import com.promel.api.domain.model.Role;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.usecase.authentication.adapter.UserAuthAdapter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public UserAuth save(UserAuth userAuth) {
        return toDomain(repository.save(toEntity(userAuth)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    private UserAuthEntity toEntity(UserAuth userAuth) {
        return modelMapper.map(userAuth, UserAuthEntity.class);
    }

    private UserAuth toDomain(UserAuthEntity entity) {

        modelMapper.typeMap(UserAuthEntity.class, UserAuth.class)
                .addMappings(mapper -> mapper.skip(UserAuth::setRoles));

        var userAuth = modelMapper.map(entity, UserAuth.class);

        userAuth.setRoles(modelMapper.map(entity.getRoles(), new TypeToken<List<Role>>() {}.getType()));

        return userAuth;
    }
}
