package com.promel.api.web.controller.user;

import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.web.controller.user.dto.UserAccountCreationRequest;
import com.promel.api.web.controller.user.dto.UserAccountResponse;
import com.promel.api.domain.model.UserAccount;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class UserAccountController {

    private UserAccountCreator userAccountCreator;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAccountController(UserAccountCreator userAccountCreator, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAccountCreator = userAccountCreator;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("public/users")
    public ResponseEntity<?> save(@RequestBody @Valid UserAccountCreationRequest userAccountCreationRequest) {
        var userAccount = toUserAccount(userAccountCreationRequest);
        userAccount.getUserAuth().setPassword(bCryptPasswordEncoder.encode(userAccount.getUserAuth().getPassword()));
        return new ResponseEntity<>(toUserAccountResponse(userAccountCreator.create(userAccount)), HttpStatus.CREATED);
    }

    private UserAccount toUserAccount(UserAccountCreationRequest request) {
        return modelMapper.map(request, UserAccount.class);
    }

    private UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        return modelMapper.map(userAccount, UserAccountResponse.class);
    }
}