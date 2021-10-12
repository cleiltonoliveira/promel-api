package com.promel.api.persistence.association;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<AssociationEntity, Long> {

     boolean existsByCnpj(String cnpj);
}
