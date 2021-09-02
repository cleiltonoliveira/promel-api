package com.promel.api.persistence.user;

import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import com.promel.api.domain.model.UserAccount;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserAccountGateway implements UserAccountAdapter {

    private ModelMapper modelMapper;
    private UserAccountRepository repository;

    public UserAccountGateway(ModelMapper modelMapper, UserAccountRepository repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        var entity = toEntity(userAccount);
        var savedAccount = toDomain(repository.save(toEntity(userAccount)));
        return savedAccount;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<UserAccount> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    private UserAccount toDomain(UserAccountEntity entity) {
        return modelMapper.map(entity, UserAccount.class);
    }

    private UserAccountEntity toEntity(UserAccount domain) {
        return modelMapper.map(domain, UserAccountEntity.class);
    }
}
