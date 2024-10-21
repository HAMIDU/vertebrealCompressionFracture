package com.karizma.onlineshopping.purchase.service.totalamount;

import com.karizma.onlineshopping.product.enumeration.DiscountType;

public class FactoryTotalAmount {

    public ContextAmountCalculate getContext(DiscountType type) {
        if (type.equals(DiscountType.PERCENTAGE)) {
            return new ContextAmountCalculate(new DiscountByPercentage());
        }
        if (type.equals(DiscountType.NO_DISCOUNT)) {
            return new ContextAmountCalculate(new ZeroDiscount());

        }
        if (type.equals(DiscountType.PRICE)) {
            return new ContextAmountCalculate(new DiscountByPrice());
        }
        return null;
    }
}
