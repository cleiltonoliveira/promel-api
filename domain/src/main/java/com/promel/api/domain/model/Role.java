package com.promel.api.domain.model;

import java.util.Objects;

public class Role {
    private Long id;
    private String role;
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return id.equals(role1.id) && role.equals(role1.role) && Objects.equals(details, role1.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, details);
    }
}
