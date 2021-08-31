package com.promel.api.persistence.gateway;

import com.promel.api.repository.UserAccountRepository;
import com.promel.api.persistence.entity.UserAccountEntity;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.persistence.repository.JpaUserAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserAccountGateway implements UserAccountRepository {

    private ModelMapper modelMapper;
    private JpaUserAccountRepository repository;

    public UserAccountGateway(ModelMapper modelMapper, JpaUserAccountRepository repository) {
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

    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserAccount> findAll() {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    private UserAccount toDomain(UserAccountEntity entity) {
        return modelMapper.map(entity, UserAccount.class);
    }

    private UserAccountEntity toEntity(UserAccount domain) {
        return modelMapper.map(domain, UserAccountEntity.class);
    }
}
