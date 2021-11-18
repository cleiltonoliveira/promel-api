package com.promel.api.persistence.hive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiveRepository extends JpaRepository<HiveEntity, Long> {

    boolean existsByIdentificationCodeAndProductionUnitId(String idCode, Long unitId);

    @Query (value = "SELECT * FROM vw_user_hives WHERE user_account_id = ?1", nativeQuery = true)
    List<HiveEntity> findAllHivesByUserId (Long userId);
}
