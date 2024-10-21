package com.karizma.onlineshopping.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "HouseholdProduct")
public class HouseholdProduct implements Serializable {


    private static final long serialVersionUID = 210626162173359411L;

    @Id
    @Column(name = "productId")
    private Long productId;

    @JoinColumn(name = "ProductId", insertable = false, updatable = false, referencedColumnName = "id")
    @OneToOne
    private Product product;

    private String color;

    private String model;

}
