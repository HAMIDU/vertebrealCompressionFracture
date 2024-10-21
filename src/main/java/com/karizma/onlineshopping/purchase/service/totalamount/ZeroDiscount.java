package com.karizma.onlineshopping.purchase.service.totalamount;

public class ZeroDiscount implements TotalAmountStrategy {


    @Override
    public long calculation(long unitAmount, long count, long discount) {
        return Math.multiplyExact(unitAmount, count);
    }
}
