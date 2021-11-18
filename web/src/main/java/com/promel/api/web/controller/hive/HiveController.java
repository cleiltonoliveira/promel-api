package com.promel.api.web.controller.hive;

import com.promel.api.domain.model.Hive;
import com.promel.api.usecase.hive.HiveCreator;
import com.promel.api.usecase.hive.HiveFinder;
import com.promel.api.web.controller.hive.dto.HiveCreationRequest;
import com.promel.api.web.controller.hive.dto.HiveResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class HiveController {
    private HiveCreator hiveCreator;
    private HiveFinder hiveFinder;
    private ModelMapper modelMapper;

    @PostMapping("protected/hives")
    public ResponseEntity<?> createHive(@RequestBody @Valid HiveCreationRequest request, Principal principal) {
        var hive = hiveCreator.create(toHive(request), principal.getName());
        return new ResponseEntity<>(toHiveResponse(hive), HttpStatus.CREATED);
    }

    @GetMapping("protected/hives")
    public ResponseEntity<?> findAllHivesByUserId (Long userId) {
        var hives = hiveFinder.findAllHiveByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(hives.stream().map(this::toHiveResponse));
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
