package com.karizma.onlineshopping.purchase.service.totalamount;

public class DiscountByPercentage implements TotalAmountStrategy {


    @Override
    public long calculation(long unitAmount, long count, long discount) {

        return Math.multiplyExact(Math.subtractExact(unitAmount,
                Math.floorDiv(Math.multiplyExact(unitAmount, discount), 100)), count);

    }
}
