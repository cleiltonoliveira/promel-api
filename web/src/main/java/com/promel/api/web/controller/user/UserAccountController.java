package com.promel.api.web.controller.user;

import com.promel.api.usecase.user.UserAccountCreator;
import com.promel.api.web.controller.user.dto.UserAccountCreationRequest;
import com.promel.api.web.controller.user.dto.UserAccountResponse;
import com.promel.api.domain.model.UserAccount;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public UserAccountController(UserAccountCreator userAccountCreator, ModelMapper modelMapper) {
        this.userAccountCreator = userAccountCreator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("public/users")
    public ResponseEntity<?> save(@RequestBody @Valid UserAccountCreationRequest userAccountCreationRequest) {
        var savedUser = userAccountCreator.create(toUserAccount(userAccountCreationRequest));
        return new ResponseEntity<>(toUserAccountResponse(savedUser), HttpStatus.CREATED);
    }

    private UserAccount toUserAccount(UserAccountCreationRequest request) {
        return modelMapper.map(request, UserAccount.class);
    }

    private UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        return modelMapper.map(userAccount, UserAccountResponse.class);
    }
}