package com.karizma.onlineshopping.purchase.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.purchase.entity.Basket;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends GenericRepository<Basket, Long> {
}
