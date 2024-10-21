package com.karizma.onlineshopping.product.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.product.entity.HouseholdProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdProductRepository extends GenericRepository<HouseholdProduct, Long> {
}
