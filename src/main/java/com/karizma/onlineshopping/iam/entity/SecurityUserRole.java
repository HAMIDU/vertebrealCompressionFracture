package com.karizma.onlineshopping.iam.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "SecurityUserRole")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityUserRole implements Serializable {

    private static final long serialVersionUID = -2022685367519533872L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SecurityUserId")
    private SecurityUser securityUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId")
    private Role role;
}
