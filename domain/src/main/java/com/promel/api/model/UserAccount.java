package com.promel.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserAccount {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private String phone;
    private Association association;
    private UserAuth userAuth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return id.equals(that.id) && name.equals(that.name) && creationDate.equals(that.creationDate) && Objects.equals(phone, that.phone) && Objects.equals(association, that.association) && userAuth.equals(that.userAuth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, phone, association, userAuth);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", phone='" + phone + '\'' +
                ", association=" + association +
                ", userAuth=" + userAuth +
                '}';
    }
}
