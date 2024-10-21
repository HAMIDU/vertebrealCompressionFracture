package com.karizma.onlineshopping.iam.service.impl;


import com.karizma.onlineshopping.base.CompositePasswordEncoderService;
import com.karizma.onlineshopping.base.JWTUtils;
import com.karizma.onlineshopping.base.OnlineShoppingPropertiesWrapper;
import com.karizma.onlineshopping.base.ValidationUtility;
import com.karizma.onlineshopping.base.exception.CoreException;
import com.karizma.onlineshopping.base.exception.FaultCode;
import com.karizma.onlineshopping.iam.dto.LoginRequestDto;
import com.karizma.onlineshopping.iam.dto.RegistrationRequestDto;
import com.karizma.onlineshopping.iam.entity.Role;
import com.karizma.onlineshopping.iam.entity.SecurityUser;
import com.karizma.onlineshopping.iam.entity.SecurityUserRole;
import com.karizma.onlineshopping.iam.entity.UserProfile;
import com.karizma.onlineshopping.iam.repository.RoleRepository;
import com.karizma.onlineshopping.iam.repository.SecurityUserRepository;
import com.karizma.onlineshopping.iam.repository.SecurityUserRoleRepository;
import com.karizma.onlineshopping.iam.repository.UserProfileRepository;
import com.karizma.onlineshopping.iam.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.karizma.onlineshopping.base.exception.FaultCode.MOBILE_NUMBER_REGISTERED_BEFORE;
import static com.karizma.onlineshopping.base.exception.FaultCode.USERNAME_DECLARED_BEFORE;


@Service
@Slf4j
public class SecurityUserServiceImpl implements SecurityUserService {

    private final SecurityUserRepository securityUserRepository;
    private final JWTUtils jwtUtils;
    private final CompositePasswordEncoderService passwordEncoder;
    private final SecurityUserRoleRepository securityUserRoleRepository;
    private final OnlineShoppingPropertiesWrapper onlineShoppingPropertiesWrapper;
    private final UserProfileRepository userProfileRepository;
    private final RoleRepository roleRepository;

    public SecurityUserServiceImpl(SecurityUserRepository securityUserRepository, JWTUtils jwtUtils,
                                   CompositePasswordEncoderService passwordEncoder,
                                   SecurityUserRoleRepository securityUserRoleRepository,
                                   OnlineShoppingPropertiesWrapper onlineShoppingPropertiesWrapper,
                                   UserProfileRepository userProfileRepository, RoleRepository roleRepository) {
        this.securityUserRepository = securityUserRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.securityUserRoleRepository = securityUserRoleRepository;
        this.onlineShoppingPropertiesWrapper = onlineShoppingPropertiesWrapper;
        this.userProfileRepository = userProfileRepository;

        this.roleRepository = roleRepository;
    }


    @Override
    public String login(LoginRequestDto loginDTO) {


        var securityUser = securityUserRepository.findSecurityUserByUsernameAndAndIsEnabled(
                loginDTO.getUsername(), Boolean.TRUE);
        if (securityUser == null) {
            throw new CoreException(FaultCode.INVALID_CREDENTIALS);
        }

        if (Boolean.FALSE.equals(securityUser.getIsEnabled())) {
            throw new CoreException(FaultCode.USER_IS_NOT_ENABLED);
        }

        var newHasPassword = passwordEncoder.encode(
                loginDTO.getUsername(), loginDTO.getPassword());

        if (!passwordEncoder.check(newHasPassword, securityUser.getPassword())) {
            throw new CoreException(FaultCode.INVALID_CREDENTIALS);
        }


        var securityUserRoleSet = securityUser.getSecurityUserRoleSet();
        var roles = securityUserRoleSet.stream().map(SecurityUserRole::getRole).
                map(Role::getName).collect(Collectors.toList());

        userProfileRepository.findById(securityUser.getId()).orElseThrow(() ->
                new CoreException(FaultCode.USER_IS_NOT_ENABLED));

        //Create Token
        Map<String, Object> claims = new TreeMap<>();
        claims.put("securityUserId", securityUser.getId());
        claims.put("role", roles);

        return jwtUtils.generateToken(claims, securityUser.getUsername(), onlineShoppingPropertiesWrapper.getTokenValidity());

    }

    @Override
    @Transactional
    public UserProfile register(RegistrationRequestDto registrationRequestDto) {

        if (securityUserRepository.findSecurityUserByUsernameAndAndIsEnabled(registrationRequestDto.getUsername(),
                registrationRequestDto.getIsEnabled()) != null) {
            throw new CoreException(USERNAME_DECLARED_BEFORE);
        }
        if (userProfileRepository.findUserProfileByMobile(registrationRequestDto.getMobile()) != null) {
            throw new CoreException(MOBILE_NUMBER_REGISTERED_BEFORE);
        }
        checkPassword(registrationRequestDto.getUsername(), registrationRequestDto.getPassword());
        var securityUser = saveSecurityUser(registrationRequestDto.getUsername(),
                registrationRequestDto.getPassword());


        Optional<Role> role = roleRepository.findById(onlineShoppingPropertiesWrapper.getDefaultRoleId());

        securityUserRoleRepository.save(SecurityUserRole.builder().securityUser(securityUser)
                .role(role.orElseGet(() -> roleRepository.save(Role.builder()
                        .id(onlineShoppingPropertiesWrapper.getDefaultRoleId())
                        .name(onlineShoppingPropertiesWrapper.getDefaultRoleName()).build())))
                .build());

        return userProfileRepository.save(UserProfile.builder().id(securityUser.getId()).firstName(registrationRequestDto.getFirstName())
                .lastName(registrationRequestDto.getLastName()).email(registrationRequestDto.getEmail())
                .gender(registrationRequestDto.getGender())
                .mobile(registrationRequestDto.getMobile()).id(securityUser.getId()).build());
    }

    @Transactional
    public SecurityUser saveSecurityUser(String username, String password) {
        var bcryptPassword = passwordEncoder.encode(username, password);
        var securityUser = SecurityUser.builder().registrationDate(LocalDate.now()).username(username).
                password(bcryptPassword)
                .isEnabled(Boolean.TRUE).build();
        return securityUserRepository.save(securityUser);

    }

    private void checkPassword(String username, String password) {
        if (password.toLowerCase().contains(username.toLowerCase())) {
            log.error(FaultCode.PASSWORD_CONTAIN_USERNAME.getMessage());
            throw new CoreException(FaultCode.PASSWORD_CONTAIN_USERNAME);
        }

        if (password.length() > onlineShoppingPropertiesWrapper.getMaximumPasswordLength()) {
            throw new CoreException(FaultCode.PASSWORD_TOO_LONG_ERROR);
        }

        if (!ValidationUtility.validatePassword(password)) {
            log.error(FaultCode.PASSWORD_PATTERN_INVALID.getMessage());
            throw new CoreException(FaultCode.PASSWORD_PATTERN_INVALID);
        }
    }



}
