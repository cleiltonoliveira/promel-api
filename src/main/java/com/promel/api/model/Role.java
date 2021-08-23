package com.promel.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "role")
public class Role extends AbstractEntity {
    @NotBlank
    @Column(name = "role")
    private String role;

    @Column(name = "details")
    private String details;
}
