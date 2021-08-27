package com.promel.api.model;

import java.util.Objects;

public class UserAuth {
    private Long id;
    private String password;
    private String email;
    private com.promel.api.entity.Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public com.promel.api.entity.Role getRole() {
        return role;
    }

    public void setRole(com.promel.api.entity.Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuth userAuth = (UserAuth) o;
        return id.equals(userAuth.id) && password.equals(userAuth.password) && email.equals(userAuth.email) && role.equals(userAuth.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email, role);
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
