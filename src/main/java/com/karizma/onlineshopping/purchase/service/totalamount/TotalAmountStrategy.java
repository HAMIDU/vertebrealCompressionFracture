package com.karizma.onlineshopping.purchase.service.totalamount;

public interface TotalAmountStrategy {

     long calculation(long unitAmount, long count, long discount );

}
