package com.karizma.onlineshopping.iam.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.iam.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {

}
