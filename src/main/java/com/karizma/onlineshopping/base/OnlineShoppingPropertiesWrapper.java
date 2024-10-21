package com.karizma.onlineshopping.base;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OnlineShoppingPropertiesWrapper {


    @Value("${secret.key}")
    private String secretKey;

    @Value("${token.validity}")
    private Integer tokenValidity;

    @Value("${password.policy.max.length}")
    private Integer maximumPasswordLength;


    @Value("${minimum.password.length}")
    private Short minimumPasswordLength;

    @Value("${user.default.role.id}")
    private Long defaultRoleId;

    @Value("${user.default.role.name}")
    private String defaultRoleName;

    @Value("${min.total.receipt.amount}")
    private Long minTotalReceiptAmount;

    @Value("${max.ongoing.purchase.minutes}")
    private Long maxOnGoingPurchaseMinutes;


}
