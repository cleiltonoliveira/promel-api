package com.promel.api.usecase.role;

import com.promel.api.domain.model.Role;

public interface FindRole {

    Role findUserRole();

    Role findAdminRole();
}
