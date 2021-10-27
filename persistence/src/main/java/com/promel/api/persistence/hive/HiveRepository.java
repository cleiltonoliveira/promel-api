package com.promel.api.persistence.hive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveRepository extends JpaRepository<HiveEntity, Long> {

    boolean existsByIdentificationCodeAndProductionUnitId(String idCode, Long unitId);
}
