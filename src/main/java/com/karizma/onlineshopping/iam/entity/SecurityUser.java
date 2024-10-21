package com.karizma.onlineshopping.iam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "SecurityUser")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SecurityUser implements Serializable {

    private static final long serialVersionUID = 1418273228798736535L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "RegistrationDate")
    private LocalDate registrationDate;

    @NotNull
    @Column(name = "Username", unique = true, length = 30)
    private String username;

    @NotNull
    @Column(name = "Password", columnDefinition = "binary(32)")
    private byte[] password;

    @Column(name = "IsEnabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "securityUser")
    private Set<SecurityUserRole> securityUserRoleSet;

}
