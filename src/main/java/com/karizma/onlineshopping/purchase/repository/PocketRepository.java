package com.karizma.onlineshopping.purchase.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.purchase.entity.Pocket;
import org.springframework.stereotype.Repository;

@Repository
public interface PocketRepository extends GenericRepository<Pocket, Long> {

}
