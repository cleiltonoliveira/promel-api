package com.promel.api.usecase.association.adapter;

import com.promel.api.domain.model.Association;

import java.util.Optional;

public interface AssociationAdapter {

    Association save(Association association);

    Optional<Association> findById(Long id);

    boolean existsByCnpj(String cnpj);

    boolean existsById(Long id);

    boolean existsByInviteCode(String inviteCode);

    Optional<Association> findByInviteCode(String inviteCode);
}
