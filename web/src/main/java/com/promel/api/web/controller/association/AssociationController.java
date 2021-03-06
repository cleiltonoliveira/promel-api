package com.promel.api.web.controller.association;

import com.promel.api.domain.model.Association;
import com.promel.api.usecase.association.AssociationCreator;
import com.promel.api.usecase.association.AssociationFinder;
import com.promel.api.usecase.association.AssociationUpdater;
import com.promel.api.usecase.user.UserAccountUpdater;
import com.promel.api.web.controller.association.dto.AssociationCreationRequest;
import com.promel.api.web.controller.association.dto.AssociationResponse;
import com.promel.api.web.controller.association.dto.InviteCodeUpdateRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class AssociationController {

    private AssociationCreator associationCreator;
    private ModelMapper modelMapper;
    private UserAccountUpdater userAccountUpdater;
    private AssociationUpdater associationUpdater;
    private AssociationFinder associationFinder;

    @PostMapping("protected/associations")
    public ResponseEntity<?> createAssociation(@RequestBody @Valid AssociationCreationRequest associationCreationRequest, Principal principal) {
        var association = associationCreator.create(toAssociation(associationCreationRequest));
        linkManagerUserToAssociation(association.getId(), principal.getName());
        return new ResponseEntity<>(toAssociationResponse(association), HttpStatus.CREATED);
    }

    @PatchMapping(value = "admin/associations/{id}")
    public ResponseEntity<?> updateInviteCode(@Valid @RequestBody InviteCodeUpdateRequest request, @PathVariable(name = "id") Long id, Principal principal) {
        associationUpdater.updateInviteCode(request.getInviteCode(), id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("protected/associations/{id}")
    public ResponseEntity<?> findAccount(@PathVariable Long id) {
        return new ResponseEntity<>(toAssociationResponse(associationFinder.findById(id)), HttpStatus.OK);
    }

    @GetMapping("protected/associations/{id}/members")
    public ResponseEntity<?> findAssociationMembers(@PathVariable Long id) {
        return new ResponseEntity<>(associationFinder.findAssociationMembers(id), HttpStatus.OK);
    }

    private void linkManagerUserToAssociation(Long associationId, String userEmail) {
        userAccountUpdater.linkUserToAssociation(associationId, userEmail);
    }

    private Association toAssociation(AssociationCreationRequest request) {
        return modelMapper.map(request, Association.class);
    }

    private AssociationResponse toAssociationResponse(Association association) {
        return modelMapper.map(association, AssociationResponse.class);
    }
}
