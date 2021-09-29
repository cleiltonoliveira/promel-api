package com.promel.api.web.controller.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.AssociationCreator;
import com.promel.api.web.controller.association.dto.AssociationCreationRequest;
import com.promel.api.web.controller.association.dto.AssociationResponse;
import org.modelmapper.ModelMapper;
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
public class AssociationController {

    private AssociationCreator associationCreator;
    private ModelMapper modelMapper;

    public AssociationController(AssociationCreator associationCreator, ModelMapper modelMapper) {
        this.associationCreator = associationCreator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("protected/associations")
    public ResponseEntity<?> createAssociation(@RequestBody @Valid AssociationCreationRequest associationCreationRequest, Principal principal) {
        var association = associationCreator.create(toAssociation(associationCreationRequest));

        return new ResponseEntity<>(toAssociationResponse(association), HttpStatus.CREATED);
    }

    private Association toAssociation(AssociationCreationRequest request) {
        return modelMapper.map(request, Association.class);
    }

    private AssociationResponse toAssociationResponse(Association association) {
        return modelMapper.map(association, AssociationResponse.class);
    }
}
