package com.karizma.onlineshopping.iam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserProfile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 3832626162173359411L;

    @Id
    @Column(name = "securityUserId")
    @NotEmpty
    private Long id;

    @NotEmpty
    @Column(length = 30)
    private String firstName;

    @NotEmpty
    @Column(length = 30)
    private String lastName;


    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 15, unique = true)
    @NotEmpty
    private String mobile;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "province", joinColumns = @JoinColumn(name = "province_id", referencedColumnName = "id")),
            @AssociationOverride(name = "City", joinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
    })
    private Place currentPlace;

    private Boolean gender;   //1 means male, 0 is female  , null is other

    private String address;
}
