package com.promel.api.web.controller.user;

import com.promel.api.domain.model.Role;
import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.usecase.user.UserAccountFinder;
import com.promel.api.usecase.user.UserAccountUpdater;
import com.promel.api.web.controller.user.dto.UserAccountCreationRequest;
import com.promel.api.web.controller.user.dto.UserAccountResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserAccountController {

    private UserAccountCreator userAccountCreator;
    private UserAccountUpdater userAccountUpdater;
    private UserAccountFinder userAccountFinder;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("public/users")
    public ResponseEntity<?> save(@RequestBody @Valid UserAccountCreationRequest userAccountCreationRequest) {
        var userAccount = toUserAccount(userAccountCreationRequest);
        userAccount.getUserAuth().setPassword(bCryptPasswordEncoder.encode(userAccount.getUserAuth().getPassword()));
        return new ResponseEntity<>(toUserAccountResponse(userAccountCreator.create(userAccount)), HttpStatus.CREATED);
    }

    @PatchMapping("protected/users/association")
    public ResponseEntity<?> joinAssociation(@RequestParam("inviteCode") String inviteCode, Principal principal) {
        userAccountUpdater.joinAssociation(inviteCode, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("protected/users/account")
    public ResponseEntity<?> findAccount(Principal principal) {
        return new ResponseEntity<>(toUserAccountResponse(userAccountFinder.findByEmail(principal.getName())), HttpStatus.OK);
    }

    private UserAccount toUserAccount(UserAccountCreationRequest request) {
        return modelMapper.map(request, UserAccount.class);
    }

    private UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        modelMapper.typeMap(UserAccount.class, UserAccountResponse.class)
                .addMappings(mapper -> mapper.skip(UserAccountResponse::setRoles));
        var roles = userAccount.getUserAuth().getRoles().stream().map(Role::getRole).collect(Collectors.toList());
        var response = modelMapper.map(userAccount, UserAccountResponse.class);
        response.setRoles(roles);
        return response;
    }
}