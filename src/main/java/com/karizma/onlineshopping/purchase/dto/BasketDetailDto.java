package com.karizma.onlineshopping.purchase.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BasketDetailDto {

    @NotEmpty
    private Long basketId;

    @NotEmpty
    @Min(1)
    private Integer count;
}
