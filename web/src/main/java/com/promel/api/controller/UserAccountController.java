package com.promel.api.controller;

import com.promel.api.usecase.user.CreateUserAccount;
import com.promel.api.dto.transport.request.UserAccountInput;
import com.promel.api.dto.transport.response.UserAccountOutput;
import com.promel.api.model.UserAccount;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class UserAccountController {

    private CreateUserAccount createUserAccount;
    private ModelMapper modelMapper;

    public UserAccountController(CreateUserAccount createUserAccount, ModelMapper modelMapper) {
        this.createUserAccount = createUserAccount;
        this.modelMapper = modelMapper;
    }

    @PostMapping("public/users")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@RequestBody @Valid UserAccountInput userInput) {
        var savedUser = createUserAccount.execute(toUserAccountDomain(userInput));
        return new ResponseEntity<>(toUserAccountOutput(savedUser), HttpStatus.CREATED);
    }

    private UserAccount toUserAccountDomain(UserAccountInput userInput) {
        return modelMapper.map(userInput, UserAccount.class);
    }

    private UserAccountOutput toUserAccountOutput(UserAccount userAccount) {
        return modelMapper.map(userAccount, UserAccountOutput.class);
    }
}