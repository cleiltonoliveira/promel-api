package com.promel.api.persistence.association;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssociationRepository extends JpaRepository<AssociationEntity, Long> {

    boolean existsByCnpj(String cnpj);

    boolean existsByInviteCode(String inviteCode);

    Optional<AssociationEntity> findByInviteCode(String inviteCode);

    @Query(value = "SELECT ua.name FROM  user_account ua WHERE ua.association_id = ?1", nativeQuery = true)
    List<String> findAssociationMembers(Long associationId);
}
