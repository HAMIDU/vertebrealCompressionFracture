package com.karizma.onlineshopping.purchase.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.purchase.entity.Basket;
import com.karizma.onlineshopping.purchase.entity.BasketProduct;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface BasketProductRepository extends GenericRepository<BasketProduct, Long> {

   List<BasketProduct> findByBasket(@NotEmpty Basket basket);
}
