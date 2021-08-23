package com.promel.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_auth")
public class UserAuth extends AbstractEntity {

    @NotBlank
    @Column(name = "name")
    private String password;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id_id")
    private Role role;
}
