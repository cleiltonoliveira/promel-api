package com.promel.api.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "association")
public class AssociationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "invite_code")
    private String inviteCode;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}

