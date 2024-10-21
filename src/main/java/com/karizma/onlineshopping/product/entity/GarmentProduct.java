package com.karizma.onlineshopping.product.entity;

import com.karizma.onlineshopping.product.enumeration.GarmentCategory;
import com.karizma.onlineshopping.product.enumeration.GarmentSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "GarmentProduct")
public class GarmentProduct implements Serializable {
    private static final long serialVersionUID = -200026162173359411L;

    @Id
    @Column(name = "productId")
    private Long productId;

    @JoinColumn(name = "ProductId", insertable = false, updatable = false, referencedColumnName = "id")
    @OneToOne
    private Product product;

    private String color;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private GarmentCategory garmentCategory;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private GarmentSize garmentSize;
}
