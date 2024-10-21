package com.karizma.onlineshopping.purchase.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.purchase.entity.Purchase;
import com.karizma.onlineshopping.purchase.enumeration.PurchaseStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends GenericRepository<Purchase, Long> {

    List<Purchase> findByStatus(PurchaseStatus status);
}
