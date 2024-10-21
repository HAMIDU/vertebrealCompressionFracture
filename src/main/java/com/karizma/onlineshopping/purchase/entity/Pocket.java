package com.karizma.onlineshopping.purchase.entity;

import com.karizma.onlineshopping.purchase.enumeration.DeliveryStatus;
import com.karizma.onlineshopping.purchase.enumeration.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pocket")
public class Pocket implements Serializable {

    private static final long serialVersionUID = -1285266919876008993L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "purchaseId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Purchase purchase;


    @NotEmpty
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDate deliveryDate;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    DeliveryStatus status;

}

