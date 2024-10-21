package com.karizma.onlineshopping.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository<T, PK extends Serializable> extends JpaRepository<T, PK> {
}
