package com.promel.api.web.controller.hive;

import com.promel.api.domain.model.Hive;
import com.promel.api.usecase.hive.HiveCreator;
import com.promel.api.web.controller.hive.dto.HiveCreationRequest;
import com.promel.api.web.controller.hive.dto.HiveResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class HiveController {
    private HiveCreator hiveCreator;
    private ModelMapper modelMapper;

    @PostMapping("protected/hives")
    public ResponseEntity<?> createHive(@RequestBody @Valid HiveCreationRequest request, Principal principal) {
        var hive = hiveCreator.create(toHive(request), principal.getName());
        return new ResponseEntity<>(toHiveResponse(hive), HttpStatus.CREATED);
    }

    private Hive toHive(HiveCreationRequest request) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(request, Hive.class);
    }

    private HiveResponse toHiveResponse(Hive hive) {
        return modelMapper.map(hive, HiveResponse.class);
    }
}
