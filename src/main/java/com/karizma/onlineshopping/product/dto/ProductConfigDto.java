package com.karizma.onlineshopping.product.dto;

import com.karizma.onlineshopping.product.enumeration.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfigDto {

    @NotEmpty
    private Long productId;

    @NotEmpty
    private Long availableProductCount;

    @NotEmpty
    private Long unitPrice;

    private String description;

    @NotNull
    private DiscountType discountType ;

    private Long discount;

    private Boolean active;
}
