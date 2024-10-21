package com.karizma.onlineshopping.purchase.service;

import com.karizma.onlineshopping.purchase.dto.BasketDto;
import com.karizma.onlineshopping.purchase.dto.PurchaseChangeStatusDto;
import com.karizma.onlineshopping.purchase.dto.PurchaseFinalizeDto;
import com.karizma.onlineshopping.purchase.entity.Basket;
import com.karizma.onlineshopping.purchase.entity.Purchase;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface PurchaseService {


    Basket createNewBasket(@NotEmpty List<BasketDto> basketDtos, @NotEmpty Long userProfileId);

    Purchase createNewPurchase(@NotEmpty Basket basket, Long userProfileId);

    Purchase purchaseFinalize(@Valid PurchaseFinalizeDto purchaseFinalizeDto, Long userProfileId);

    void cancelledIncompletePurchase();

    Purchase changePurchaseStatus(@Valid PurchaseChangeStatusDto purchaseChangeStatusDto);

}
