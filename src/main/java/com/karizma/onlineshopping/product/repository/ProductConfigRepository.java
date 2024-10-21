package com.karizma.onlineshopping.product.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.product.entity.ProductConfig;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface ProductConfigRepository extends GenericRepository<ProductConfig, Long> {

    Optional<ProductConfig> findByProductIdAndActive(@NotEmpty Long productId, @NotEmpty Boolean active);
}
