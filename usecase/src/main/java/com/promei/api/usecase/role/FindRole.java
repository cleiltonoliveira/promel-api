package com.promei.api.usecase.role;

import com.promel.api.model.Role;

public interface FindRole {

    Role findUserRole();

    Role findAdminRole();
}
