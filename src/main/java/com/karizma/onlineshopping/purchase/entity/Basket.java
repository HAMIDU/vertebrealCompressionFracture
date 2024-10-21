package com.karizma.onlineshopping.purchase.entity;

import com.karizma.onlineshopping.iam.entity.UserProfile;
import com.karizma.onlineshopping.purchase.enumeration.BasketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Basket")
public class Basket implements Serializable {

    private static final long serialVersionUID = -7285266619876008993L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userProfileId", nullable = false)
    @ManyToOne
    private UserProfile userProfile;


    @Column(name = "reservationDate")
    @NotEmpty
    @Builder.Default
    private LocalDateTime reservationDate = LocalDateTime.now();


    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    BasketStatus status;

}
