package com.promel.api.usecase.user;

import com.promel.api.model.UserAccount;

public interface CreateUserAccount {
    UserAccount execute(UserAccount userAccount);
}