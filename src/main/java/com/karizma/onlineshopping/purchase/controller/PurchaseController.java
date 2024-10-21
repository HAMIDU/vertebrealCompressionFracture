package com.karizma.onlineshopping.purchase.controller;

import com.karizma.onlineshopping.config.SecurityContext;
import com.karizma.onlineshopping.purchase.dto.BasketDto;
import com.karizma.onlineshopping.purchase.dto.PurchaseFinalizeDto;
import com.karizma.onlineshopping.purchase.entity.Basket;
import com.karizma.onlineshopping.purchase.enumeration.PurchaseStatus;
import com.karizma.onlineshopping.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/purchase")
@Slf4j
@Tag(name = "Product Purchase APIs", description = "purchase Apis")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final SecurityContext securityContext;

    public PurchaseController(PurchaseService purchaseService, SecurityContext securityContext) {
        this.purchaseService = purchaseService;
        this.securityContext = securityContext;
    }

    @PostMapping("/basket/create")
    public Basket createProductBasket(@Valid @RequestBody List<BasketDto> basketDto) {
        return purchaseService.createNewBasket(basketDto, securityContext.getSecurityUserId());
    }

    @PostMapping("/purchse/create/{basketId}")
    public Long createProductBasket(@PathVariable Long basketId) {
        return purchaseService.createNewPurchase(Basket.builder().id(basketId).build(),
                securityContext.getSecurityUserId()).getId();
    }

    @PostMapping("/purchse/finalize")
    public Long purchaseFinalize(@RequestBody @Valid PurchaseFinalizeDto purchaseFinalizeDto) {
        purchaseFinalizeDto.setStatus(PurchaseStatus.SUBMITTED);
        return purchaseService.purchaseFinalize(purchaseFinalizeDto,
                securityContext.getSecurityUserId() ).getId();
    }

}
