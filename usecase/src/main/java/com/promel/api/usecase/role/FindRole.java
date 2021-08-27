package com.promel.api.usecase.role;

import com.promel.api.model.Role;

public interface FindRole {

    Role findUserRole();

    Role findAdminRole();
}
