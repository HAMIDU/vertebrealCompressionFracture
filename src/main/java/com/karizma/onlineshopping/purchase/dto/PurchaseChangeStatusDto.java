package com.karizma.onlineshopping.purchase.dto;

import com.karizma.onlineshopping.purchase.enumeration.PurchaseStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PurchaseChangeStatusDto {

    @NotEmpty
    private Long purchaseId;

    @NotEmpty
    private PurchaseStatus status;
}
