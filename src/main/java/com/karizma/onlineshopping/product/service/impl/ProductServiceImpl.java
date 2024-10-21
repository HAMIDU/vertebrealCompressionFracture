package com.karizma.onlineshopping.product.service.impl;

import com.karizma.onlineshopping.base.exception.CoreException;
import com.karizma.onlineshopping.base.exception.FaultCode;
import com.karizma.onlineshopping.product.dto.ProductRequestDto;
import com.karizma.onlineshopping.product.entity.*;
import com.karizma.onlineshopping.product.repository.*;
import com.karizma.onlineshopping.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FoodstuffProductRepository foodstuffProductRepository;
    private final GarmentProductRepository garmentProductRepository;
    private final HouseholdProductRepository householdProductRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductConfigRepository productConfigRepository;

    public ProductServiceImpl(ProductRepository productRepository, FoodstuffProductRepository foodstuffProductRepository, GarmentProductRepository garmentProductRepository, HouseholdProductRepository householdProductRepository, ProductCategoryRepository productCategoryRepository, ProductConfigRepository productConfigRepository) {
        this.productRepository = productRepository;
        this.foodstuffProductRepository = foodstuffProductRepository;
        this.garmentProductRepository = garmentProductRepository;
        this.householdProductRepository = householdProductRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productConfigRepository = productConfigRepository;
    }

    @Override
    @Transactional
    public Product save(ProductRequestDto productRequestDto) {
        var productCategory = productCategoryRepository.
                findByIdAndActive(productRequestDto.getProductCategoryId(),Boolean.TRUE)
                .orElseThrow(() -> new CoreException(FaultCode.NOT_FOUND_ACTIVE_CATEGORY));

        var savedProduct = productRepository.save(Product.builder().createDate(LocalDateTime.now()).productCategory(productCategory)
                .name(productRequestDto.getName().strip()).productionDate(productRequestDto.getProductionDate())
                .active(productRequestDto.getActive())
                .packingType(productRequestDto.getPackingType())
                .build());

        switch (productCategory.getProductType()) {
            case GARMENT -> garmentProductRepository.save(GarmentProduct.builder().productId(savedProduct.getId())
                    .color(productRequestDto.getColor())
                    .garmentCategory(productRequestDto.getGarmentCategory())
                    .garmentSize(productRequestDto.getGarmentSize())
                    .build());
            case FOODSTUFF -> foodstuffProductRepository.save(FoodstuffProduct.builder().productId(savedProduct.getId())
                    .expireDate(productRequestDto.getExpireDate())
                    .build());
            case HOUSEHOLD -> householdProductRepository.save(HouseholdProduct.builder().productId(savedProduct.getId())
                    .color(productRequestDto.getColor())
                    .model(productRequestDto.getModel())
                    .build());


        }
        return savedProduct;
    }

    @Override
    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
    public ProductConfig save(ProductConfig productConfig) {
        return productConfigRepository.save(productConfig);
    }
}
