package com.promel.api.web.controller.user;

import com.promel.api.domain.model.UserAccount;
import com.promel.api.usecase.user.UserAccountCreator;
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

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserAccountController {

    private UserAccountCreator userAccountCreator;
    private UserAccountUpdater userAccountUpdater;
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

    private UserAccount toUserAccount(UserAccountCreationRequest request) {
        return modelMapper.map(request, UserAccount.class);
    }

    private UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        return modelMapper.map(userAccount, UserAccountResponse.class);
    }
}