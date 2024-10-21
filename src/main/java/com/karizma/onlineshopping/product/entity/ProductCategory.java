package com.karizma.onlineshopping.product.entity;

import com.karizma.onlineshopping.product.enumeration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ProductCategory")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = -4698208336884411654L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "isActive")
    private Boolean active ;

    @Column(name = "createDate")
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ProductType productType;


}
