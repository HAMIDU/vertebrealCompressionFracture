package com.karizma.onlineshopping.purchase.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BasketDto {

    @NotEmpty
    private Long productId;

    @NotEmpty
    @Min(1)
    private Long count;

}
