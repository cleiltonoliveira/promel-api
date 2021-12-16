package com.promel.api.web.controller.production;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.production.HoneyProductionCreator;
import com.promel.api.web.controller.production.dto.HoneyProductionResponse;
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
public class HoneyProductionController {
    private ModelMapper modelMapper;
    private HoneyProductionCreator honeyProductionCreator;

    @PostMapping(value = "admin/honey/production")
    public ResponseEntity<?> createHarvestProcess(Principal principal) {
        var honeyProduction = honeyProductionCreator.create(principal.getName());
        return new ResponseEntity<>(toHoneyProductionResponse(honeyProduction), HttpStatus.CREATED);
    }

    private HoneyProductionResponse toHoneyProductionResponse(HoneyProduction model) {
        return modelMapper.map(model, HoneyProductionResponse.class);
    }
}
