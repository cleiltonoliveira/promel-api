package com.promel.api.persistence.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.adapter.AssociationAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AssociationGateway implements AssociationAdapter {
    private AssociationRepository repository;
    private ModelMapper modelMapper;

    public AssociationGateway(AssociationRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Association save(Association association) {
        return toDomain(repository.save(toEntity(association)));
    }

    @Override
    public Optional<Association> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return repository.existsByCnpj(cnpj);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByInviteCode(String inviteCode) {
        return repository.existsByInviteCode(inviteCode);
    }

    @Override
    public Optional<Association> findByInviteCode(String inviteCode) {
        return repository.findByInviteCode(inviteCode).map(this::toDomain);
    }

    @Override
    public List<String> findAssociationMembers(Long associationId) {
        return repository.findAssociationMembers(associationId);
    }

    private Association toDomain(AssociationEntity entity) {
        return modelMapper.map(entity, Association.class);
    }

    private AssociationEntity toEntity(Association domain) {
        return modelMapper.map(domain, AssociationEntity.class);
    }
}
