package com.promel.api.persistence.production.hiveproductionperharvest;

import com.promel.api.domain.model.HiveProductionPerHarvestKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HiveProductionPerHarvestRepository extends JpaRepository<HiveProductionPerHarvestEntity, HiveProductionPerHarvestKey> {

    @Query(value = "SELECT IF(COUNT(*) = 1, 'true', 'false') FROM vw_honey_production_hive WHERE honey_production_id = ?1 AND hive_id = ?2", nativeQuery = true)
    boolean associationHasHoneyProductionAndHive(Long honeyProductionId, Long hiveId);
}
