package com.karizma.onlineshopping.product.service;

import com.karizma.onlineshopping.product.dto.ProductRequestDto;
import com.karizma.onlineshopping.product.entity.Product;
import com.karizma.onlineshopping.product.entity.ProductCategory;
import com.karizma.onlineshopping.product.entity.ProductConfig;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public interface ProductService {

    Product save(@Valid ProductRequestDto productRequestDto);

    ProductCategory save(@NotEmpty ProductCategory productCategory);

    ProductConfig save(@NotEmpty ProductConfig productConfig);
}
