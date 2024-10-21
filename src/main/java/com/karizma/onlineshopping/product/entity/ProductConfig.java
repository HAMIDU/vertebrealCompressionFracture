package com.karizma.onlineshopping.product.entity;


import com.karizma.onlineshopping.product.enumeration.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ProductConfig")
public class ProductConfig implements Serializable {

    private static final long serialVersionUID = 3532626169973359411L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productId")
    private Long productId;

    @JoinColumn(name = "ProductId", insertable = false, updatable = false, referencedColumnName = "id")
    @OneToOne
    private Product product;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @NotEmpty
    private Long availableProductCount;

    @NotEmpty
    private Long unitPrice;

    @Column(name = "description", length = 300)
    private String description;

    @NotNull
    @Builder.Default
    private DiscountType discountType = DiscountType.NO_DISCOUNT;

    private Long discount;

    @Version
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long version;

    @Column(name = "isActive")
    @NotEmpty
    private Boolean active;
}
