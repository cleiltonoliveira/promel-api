package com.promel.api.usecase.hive.adapter;

import com.promel.api.domain.model.Hive;

public interface HiveAdapter {

    Hive save(Hive h);

    boolean existsByIdentificationCodeAndProductionUnitId(String idCode, Long unitId);
}
