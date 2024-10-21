package com.karizma.onlineshopping.iam.repository;

import com.karizma.onlineshopping.base.GenericRepository;
import com.karizma.onlineshopping.iam.entity.UserProfile;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

@Repository
public interface UserProfileRepository extends GenericRepository<UserProfile, Long> {

    UserProfile findUserProfileByMobile( @NotEmpty String mobile);

}
