package com.promel.api.web.controller.productionunit;

import com.promel.api.domain.model.ProductionUnit;
import com.promel.api.usecase.productiounit.ProductionUnitCreator;
import com.promel.api.web.controller.productionunit.dto.ProductionUnitResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class ProductionUnitController {

    private ProductionUnitCreator productionUnitCreator;
    private ModelMapper modelMapper;

    @PostMapping("protected/production-units")
    public ResponseEntity<?> createProductionUnit(Principal principal) {
        var unit = productionUnitCreator.create(principal.getName());
        return new ResponseEntity<>(toProductionUnitResponse(unit), HttpStatus.CREATED);
    }

    private ProductionUnitResponse toProductionUnitResponse(ProductionUnit unit) {
        return modelMapper.map(unit, ProductionUnitResponse.class);
    }
}
