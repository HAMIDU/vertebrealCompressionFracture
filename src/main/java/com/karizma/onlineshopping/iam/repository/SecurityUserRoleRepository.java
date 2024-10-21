package com.karizma.onlineshopping.iam.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.iam.entity.SecurityUserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserRoleRepository extends GenericRepository<SecurityUserRole, Long> {

}
