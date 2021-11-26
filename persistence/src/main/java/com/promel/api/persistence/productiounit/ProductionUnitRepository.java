package com.promel.api.persistence.productiounit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionUnitRepository extends JpaRepository<ProductionUnitEntity, Long> {
    Optional<ProductionUnitEntity> findFirstByUserAccountId(Long id);
}
