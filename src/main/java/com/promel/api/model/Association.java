package com.promel.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "association")
public class Association extends AbstractEntity {

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

