package com.karizma.onlineshopping.product.entity;


import com.karizma.onlineshopping.product.enumeration.PackingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Product")
public class Product implements Serializable {

    private static final long serialVersionUID = 3530626162173359411L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name", length = 100)
    private String name;

    @NotNull
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @NotEmpty
    private LocalDate productionDate;

    @ManyToOne
    @JoinColumn(name = "productCategoryId")
    @NotEmpty
    private ProductCategory productCategory;

    @Column(name = "isActive")
    private Boolean active ;

    @NotEmpty
    @Enumerated(EnumType.ORDINAL)
    private PackingType packingType;

    @OneToOne(mappedBy = "product")
    private ProductConfig productConfig;
}
