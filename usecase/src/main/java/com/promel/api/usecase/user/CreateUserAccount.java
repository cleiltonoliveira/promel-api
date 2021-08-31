package com.promel.api.usecase.user;

import com.promel.api.domain.model.UserAccount;

public interface CreateUserAccount {
    UserAccount execute(UserAccount userAccount);
}
