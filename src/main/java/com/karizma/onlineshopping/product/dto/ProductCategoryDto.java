package com.karizma.onlineshopping.product.dto;

import com.karizma.onlineshopping.product.enumeration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;

    private boolean active;

    @NotEmpty
    private ProductType productType;
}
