package com.karizma.onlineshopping.product.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.product.entity.Product;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface ProductRepository extends GenericRepository<Product, Long> {

    Optional<Product> findByIdAndActive(@NotEmpty Long id, @NotEmpty Boolean active);
}
