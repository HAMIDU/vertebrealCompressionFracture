package com.karizma.onlineshopping.purchase.controller;

import com.karizma.onlineshopping.purchase.dto.PurchaseChangeStatusDto;
import com.karizma.onlineshopping.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/admin/purchase")
@Slf4j
@Tag(name = "Product Purchase management APIs", description = "purchase mangement Apis")
public class PurchaseManagementController {

    private final PurchaseService purchaseService;

    public PurchaseManagementController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/change/status")
    public Long createProductBasket(@Valid @RequestBody PurchaseChangeStatusDto purchaseChangeStatusDto) {

        return purchaseService.changePurchaseStatus(purchaseChangeStatusDto).getId();
    }

}
