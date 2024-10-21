package com.karizma.onlineshopping.purchase.service.totalamount;

public class DiscountByPrice implements TotalAmountStrategy {


    @Override
    public long calculation(long unitAmount, long count, long discount) {

        return Math.multiplyExact(Math.subtractExact(unitAmount, discount), count);
    }
}
