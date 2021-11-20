package com.promel.api.usecase.hive;

import com.promel.api.domain.model.Hive;
import com.promel.api.usecase.hive.adapter.HiveAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class HiveFinder {

    private HiveAdapter hiveAdapter;

    @Inject
    public HiveFinder(HiveAdapter hiveAdapter) {
        this.hiveAdapter = hiveAdapter;
    }

    public List<Hive> findAllHiveByUserId (Long userId) {
        return hiveAdapter.findAllHivesByUserId(userId);
    }
}