package com.promel.api.web.controller.production.honeyproduction;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionCreator;
import com.promel.api.usecase.production.honeyproduction.HoneyProductionFinder;
import com.promel.api.usecase.user.UserAccountFinder;
import com.promel.api.web.controller.production.honeyproduction.dto.HoneyProductionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private HoneyProductionFinder honeyProductionFinder;
    private UserAccountFinder userAccountFinder;

    @PostMapping(value = "admin/association/production")
    public ResponseEntity<?> createHoneyProduction(Principal principal) {
        var honeyProduction = honeyProductionCreator.create(principal.getName());
        return new ResponseEntity<>(toHoneyProductionResponse(honeyProduction), HttpStatus.CREATED);
    }

    @GetMapping(value = "protected/association/production/in-progress")
    public ResponseEntity<?> findHoneyProductionInProgress(Principal principal) {
        var userAccount = userAccountFinder.findByEmail(principal.getName());
        var production = honeyProductionFinder.findHoneyProductionInProgress(userAccount.getAssociation().getId());
        return new ResponseEntity<>(toHoneyProductionResponse(production), HttpStatus.OK);
    }

    @GetMapping(value = "protected/association/production/history")
    public ResponseEntity<?> findAllHoneyProductionByAssociation(Principal principal) {
        var userAccount = userAccountFinder.findByEmail(principal.getName());
        var productionList = honeyProductionFinder.findAllHoneyProductionByAssociation(userAccount.getAssociation().getId());
        return new ResponseEntity<>(productionList.stream().map(this::toHoneyProductionResponse), HttpStatus.OK);
    }

    private HoneyProductionResponse toHoneyProductionResponse(HoneyProduction model) {
        return modelMapper.map(model, HoneyProductionResponse.class);
    }
}
