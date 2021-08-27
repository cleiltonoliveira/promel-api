package com.promel.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Association {
    private Long id;
    private String name;
    private String cnpj;
    private String inviteCode;
    private LocalDateTime creationDate;

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Association that = (Association) o;
        return id.equals(that.id) && name.equals(that.name) && cnpj.equals(that.cnpj) && Objects.equals(inviteCode, that.inviteCode) && creationDate.equals(that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnpj, inviteCode, creationDate);
    }
}

