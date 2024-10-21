package com.karizma.onlineshopping.iam.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.iam.entity.SecurityUser;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

@Repository
public interface SecurityUserRepository extends GenericRepository<SecurityUser, Long> {

    SecurityUser findSecurityUserByUsernameAndAndIsEnabled(@NotEmpty String username, @NotEmpty Boolean isEnabled);


}
