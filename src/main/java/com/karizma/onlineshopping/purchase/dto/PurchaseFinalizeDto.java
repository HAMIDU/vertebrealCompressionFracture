package com.karizma.onlineshopping.purchase.dto;

import com.karizma.onlineshopping.purchase.enumeration.DeliveryStatus;
import com.karizma.onlineshopping.purchase.enumeration.PurchaseStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseFinalizeDto {

    @NotEmpty
    private Long purchaseId;

    @NotBlank
    private String destinationAddress;

    private LocalDate vipDeliveryDate;

    private LocalDate regularDeliveryDate;

    private PurchaseStatus status;

    private DeliveryStatus deliveryStatus;
}
