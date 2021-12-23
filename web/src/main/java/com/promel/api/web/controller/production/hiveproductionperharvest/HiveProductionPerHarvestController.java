package com.promel.api.web.controller.production.hiveproductionperharvest;

import com.promel.api.domain.model.HiveProductionPerHarvest;
import com.promel.api.domain.model.HiveProductionPerHarvestKey;
import com.promel.api.usecase.production.hiveproductionperharvest.adapter.HiveProductionPerHarvestCreator;
import com.promel.api.web.controller.production.hiveproductionperharvest.dto.HiveProductionPerHarvestResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class HiveProductionPerHarvestController {
    private ModelMapper modelMapper;
    private HiveProductionPerHarvestCreator creator;

    @PostMapping("admin/association/production/hives")
    public ResponseEntity<?> create(@RequestParam(name = "honeyProductionId") Long honeyProductionId,
                                    @RequestParam(name = "hiveId") Long hiveId,
                                    @Valid   @RequestParam(name = "numberOfFrames" ) @Min(1) Integer numberOfFrames,
                                    Principal principal) {

        var key = new HiveProductionPerHarvestKey(honeyProductionId, hiveId);
        var hiveProductionPH = new HiveProductionPerHarvest(key, numberOfFrames, 0.0);

        var response = toHiveProductionPerHarvestResponse(creator.create(hiveProductionPH, principal.getName()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private HiveProductionPerHarvestResponse toHiveProductionPerHarvestResponse(HiveProductionPerHarvest hpph) {
        return modelMapper.map(hpph, HiveProductionPerHarvestResponse.class);
    }
}
