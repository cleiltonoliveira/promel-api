package com.promel.api.persistence.user;

import com.promel.api.persistence.association.AssociationEntity;
import com.promel.api.persistence.authentication.UserAuthEntity;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_account")
@DynamicUpdate
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private AssociationEntity association;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @Embedded
    @JoinColumn(name = "user_auth_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UserAuthEntity userAuth;
}
