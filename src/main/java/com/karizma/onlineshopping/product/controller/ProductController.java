package com.karizma.onlineshopping.product.controller;

import com.karizma.onlineshopping.product.dto.ProductCategoryDto;
import com.karizma.onlineshopping.product.dto.ProductConfigDto;
import com.karizma.onlineshopping.product.dto.ProductRequestDto;
import com.karizma.onlineshopping.product.service.ProductService;
import com.karizma.onlineshopping.purchase.mapper.ProductCategoryMapper;
import com.karizma.onlineshopping.purchase.mapper.ProductConfigMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
@Slf4j
@Tag(name = "Products APIs", description = "all authentication and authorization services")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Tag(name = "add a new  product", description = "system admin add a new product")
    @PostMapping("/add/product")
    public Long addNewProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.save(productRequestDto).getId();
    }

    @Tag(name = "add a new product category", description = "system admin can add a new product category ")
    @PostMapping("/add/product-category")
    public Long addNewProductCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto) {

        return productService.save(ProductCategoryMapper.INSTANCE.toEntity(productCategoryDto)).getId();
    }

    @Tag(name = "add a new product config", description = "system admin can add a new config on the product")
    @PostMapping("/add/product-config")
    public Long addNewProductConfig(@Valid @RequestBody ProductConfigDto productConfigDto) {
        return productService.save(ProductConfigMapper.INSTANCE.toEntity(productConfigDto)).getProduct().getId();
    }
}
