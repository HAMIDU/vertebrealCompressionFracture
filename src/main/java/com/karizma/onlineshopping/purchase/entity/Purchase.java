package com.karizma.onlineshopping.purchase.entity;

import com.karizma.onlineshopping.purchase.enumeration.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = -7285266919876008993L;

    @Id
    @Column(name = "basketId")
    private Long id;

    @JoinColumn(name = "basketId", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private Basket basket;


    @Column(name = "purchaseDate")
    @NotEmpty
    @Builder.Default
    private LocalDateTime purchaseDate = LocalDateTime.now();


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    PurchaseStatus status;

    Long receiptAmount;

    private String destinationAddress;

    private LocalDateTime lastUpdate;

}

