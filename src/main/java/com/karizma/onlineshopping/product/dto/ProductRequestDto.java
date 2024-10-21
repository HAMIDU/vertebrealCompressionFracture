package com.karizma.onlineshopping.product.dto;


import com.karizma.onlineshopping.product.enumeration.GarmentCategory;
import com.karizma.onlineshopping.product.enumeration.GarmentSize;
import com.karizma.onlineshopping.product.enumeration.PackingType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.awt.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDto {


    @NotEmpty
    @Max(100)
    private String name;

    @NotEmpty
    private LocalDate productionDate;

    @NotEmpty
    private Long productCategoryId;

    @NotEmpty
    private Boolean active;

    private LocalDate expireDate;

    private String color;

    private GarmentCategory garmentCategory;

    private GarmentSize garmentSize;

    private String model;

    @NotEmpty
    private PackingType packingType;
}
