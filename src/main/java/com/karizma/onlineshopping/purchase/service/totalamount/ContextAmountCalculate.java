package com.karizma.onlineshopping.purchase.service.totalamount;


public class ContextAmountCalculate {

    private TotalAmountStrategy strategy;

    public ContextAmountCalculate(TotalAmountStrategy strategy) {
        this.strategy = strategy;
    }

    public long executeStrategy(long unitAmount, long count, long discount) {
        return strategy.calculation(unitAmount, count, discount);
    }
}
