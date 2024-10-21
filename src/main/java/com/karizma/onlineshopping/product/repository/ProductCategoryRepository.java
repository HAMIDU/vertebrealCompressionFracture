package com.karizma.onlineshopping.product.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.product.entity.ProductCategory;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends GenericRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByIdAndActive(@NotEmpty Long id, @NotEmpty Boolean active);

}
