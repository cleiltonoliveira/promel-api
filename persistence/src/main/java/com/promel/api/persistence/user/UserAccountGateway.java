package com.promel.api.persistence.user;

import com.promel.api.domain.model.Role;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.domain.model.UserAuth;
import com.promel.api.persistence.authentication.UserAuthEntity;
import com.promel.api.usecase.user.adapter.UserAccountAdapter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
        return toDomain(repository.save(toEntity(userAccount)));
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

    @Override
    public Optional<UserAccount> findByUserAuth(UserAuth userAuth) {
        return repository.findByUserAuth(modelMapper.map(userAuth, UserAuthEntity.class)).map(this::toDomain);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email){
        return repository.findByUserAuthEmail(email).map(this::toDomain);
    }

    private UserAccount toDomain(UserAccountEntity entity) {
        var user = modelMapper.map(entity, UserAccount.class);
        user.getUserAuth().setRoles(modelMapper.map(entity.getUserAuth().getRoles(), new TypeToken<List<Role>>() {}.getType()));
        return user;
    }

    private UserAccountEntity toEntity(UserAccount domain) {
        return modelMapper.map(domain, UserAccountEntity.class);
    }
}
