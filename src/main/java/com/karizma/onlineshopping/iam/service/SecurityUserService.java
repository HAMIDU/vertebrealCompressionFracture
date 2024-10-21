package com.karizma.onlineshopping.iam.service;

import com.karizma.onlineshopping.iam.dto.LoginRequestDto;
import com.karizma.onlineshopping.iam.dto.RegistrationRequestDto;
import com.karizma.onlineshopping.iam.entity.SecurityUser;
import com.karizma.onlineshopping.iam.entity.UserProfile;

public interface SecurityUserService {

    /**
     * @param loginDTO
     * @return token with user info in payload
     */
    String login(LoginRequestDto loginDTO);

    /**
     * register new user in system
     *
     * @param registrationRequestDto
     */
    UserProfile register(RegistrationRequestDto registrationRequestDto);

    /**
     * @param username
     * @param password
     * @return saved login user
     */
    SecurityUser saveSecurityUser(String username, String password);

}
