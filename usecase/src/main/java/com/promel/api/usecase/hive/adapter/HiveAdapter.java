package com.promel.api.usecase.hive.adapter;

import com.promel.api.domain.model.Hive;

import java.util.List;

public interface HiveAdapter {

    Hive save(Hive h);

    boolean existsByIdentificationCodeAndProductionUnitId(String idCode, Long unitId);

    List<Hive> findAllHivesByUserId (Long userId);
}
