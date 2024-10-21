package com.karizma.onlineshopping.iam.controller;

import com.karizma.onlineshopping.iam.dto.LoginRequestDto;
import com.karizma.onlineshopping.iam.dto.RegistrationRequestDto;
import com.karizma.onlineshopping.iam.entity.UserProfile;
import com.karizma.onlineshopping.iam.service.SecurityUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/iam")
@Slf4j
@Tag(name = "IAM APIs", description = "all authentication and authorization services")
public class IAMController {

    private SecurityUserService securityUserService;

    public IAMController(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @PostMapping("/login")
    @Tag(name = "login", description = "customer can login ")
    public String login(@RequestBody @Valid LoginRequestDto loginDTO) {
        return securityUserService.login(loginDTO);
    }

    @PostMapping("/register")
    @Tag(name = "register", description = "customer can register in system ")
    public UserProfile register(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        return securityUserService.register(registrationRequestDto);
    }
}
