package com.promel.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_account")
public class UserAccount extends AbstractEntity {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id")
    private Association association;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_auth_id")
    @NotNull
    private UserAuth userAuth;
}
