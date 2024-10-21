package com.karizma.onlineshopping.product.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.product.entity.FoodstuffProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodstuffProductRepository extends GenericRepository<FoodstuffProduct, Long> {
}
