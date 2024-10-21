package com.kariza.onlineshopping.service;

import com.karizma.onlineshopping.base.CompositePasswordEncoderService;
import com.karizma.onlineshopping.base.JWTUtils;
import com.karizma.onlineshopping.base.OnlineShoppingPropertiesWrapper;
import com.karizma.onlineshopping.base.exception.CoreException;
import com.karizma.onlineshopping.base.exception.FaultCode;
import com.karizma.onlineshopping.iam.dto.RegistrationRequestDto;
import com.karizma.onlineshopping.iam.entity.SecurityUser;
import com.karizma.onlineshopping.iam.entity.UserProfile;
import com.karizma.onlineshopping.iam.repository.SecurityUserRepository;
import com.karizma.onlineshopping.iam.repository.UserProfileRepository;
import com.karizma.onlineshopping.iam.service.impl.SecurityUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SecurityUserServiceImpl.class, OnlineShoppingPropertiesWrapper.class})
@MockBean({JWTUtils.class, CompositePasswordEncoderService.class})
class SecurityUserServiceImplUTest {

    @Autowired
    private SecurityUserServiceImpl securityUserService;

    @MockBean
    private SecurityUserRepository securityUserRepository;


    @MockBean
    private UserProfileRepository userProfileRepository;

    @Captor
    private ArgumentCaptor<SecurityUser> securityUserCaptor;

    private SecurityUser dummyUser;
    private UserProfile dummyUserProfile;
    private RegistrationRequestDto registrationRequestDto;

    @BeforeEach
    void setup() {
        registrationRequestDto = new RegistrationRequestDto();

        dummyUserProfile = UserProfile.builder().id(1L).mobile("0989121111111").firstName("testName")
                .lastName("testFamily")
                .build();

        dummyUser = SecurityUser.builder().username("username").isEnabled(true).password("password".getBytes()).build();
    }

    @Test
    void register_duplicateUsername_throwsException() {
        //arrange
        doReturn(dummyUser).when(securityUserRepository).findSecurityUserByUsernameAndAndIsEnabled(any(), true);

        try {
            //act
            securityUserService.register(registrationRequestDto);
            fail("it throws a core exception with UNPROCESSABLE_CONTENT status. if this behaviour has been changed, " +
                    "please change me");
        } catch (CoreException ex) {
            //assert
            assertEquals(FaultCode.USERNAME_DECLARED_BEFORE.getCode(), ex.getCode());
            verify(securityUserRepository, never()).save(any());
        }
    }

    @Test
    void register_duplicatePhoneNumber_throwsException() {
        //arrange
        doReturn(dummyUserProfile).when(userProfileRepository).findUserProfileByMobile(any());

        try {
            //act
            securityUserService.register(registrationRequestDto);
            fail("it throws a core exception with UNPROCESSABLE_CONTENT status. if this behaviour has been changed, please change me");
        } catch (CoreException ex) {
            //assert
            assertEquals(FaultCode.MOBILE_NUMBER_REGISTERED_BEFORE.getCode(), ex.getCode());
            verify(securityUserRepository, never()).save(any());
        }
    }

    @Test
    void register_passwordContainsUsername_throwsException() {
        //arrange
        doReturn(null).when(securityUserRepository).findSecurityUserByUsernameAndAndIsEnabled(any(),true);
        doReturn(null).when(userProfileRepository).findUserProfileByMobile(any());
        registrationRequestDto.setPassword("usernamePassword");
        registrationRequestDto.setUsername("username");

        try {
            //act
            securityUserService.register(registrationRequestDto);
            fail("it throws a core exception with UNPROCESSABLE_CONTENT status. if this behaviour has been changed, please change me");
        } catch (CoreException ex) {
            //assert
            assertEquals(FaultCode.PASSWORD_CONTAIN_USERNAME.getCode(), ex.getCode());
            verify(securityUserRepository, never()).save(any());

        }
    }

    @Test
    void register_passwordTooLong_throwsException() {
        //arrange
        doReturn(null).when(securityUserRepository).findSecurityUserByUsernameAndAndIsEnabled(any(),true);
        doReturn(null).when(userProfileRepository).findUserProfileByMobile(any());
        registrationRequestDto.setPassword("usernamePassword");
        registrationRequestDto.setUsername("john");

        try {
            //act
            securityUserService.register(registrationRequestDto);
            fail("it throws a core exception with UNPROCESSABLE_CONTENT status. if this behaviour has been changed, please change me");
        } catch (CoreException ex) {
            //assert
            assertEquals(FaultCode.PASSWORD_TOO_LONG_ERROR.getCode(), ex.getCode());
            verify(securityUserRepository, never()).save(any());
        }
    }

    @Test
    void register_passwordInvalidPattern_throwsException() {
        //arrange
        doReturn(null).when(securityUserRepository).findSecurityUserByUsernameAndAndIsEnabled(any(),true);
        doReturn(null).when(userProfileRepository).findUserProfileByMobile(any());
        registrationRequestDto.setPassword("pass");
        registrationRequestDto.setUsername("john");

        try {
            //act
            securityUserService.register(registrationRequestDto);
            fail("it throws a core exception with UNPROCESSABLE_CONTENT status. if this behaviour has been changed, please change me");
        } catch (CoreException ex) {
            //assert
            assertEquals(FaultCode.PASSWORD_PATTERN_INVALID.getCode(), ex.getCode());
            verify(securityUserRepository, never()).save(any());
        }
    }

    @Test
    void register_happyFlow() {
        //arrange
        doReturn(null).when(securityUserRepository).findSecurityUserByUsernameAndAndIsEnabled(any(),true);
        doReturn(null).when(userProfileRepository).findUserProfileByMobile(any());
        registrationRequestDto.setPassword("Pass@123");
        registrationRequestDto.setUsername("john");

        //act
        securityUserService.register(registrationRequestDto);

        //assert
        verify(securityUserRepository).save(securityUserCaptor.capture());
        var securityUser = securityUserCaptor.getValue();
        assertEquals(registrationRequestDto.getUsername(), securityUser.getUsername());
        assertTrue(securityUser.getIsEnabled());
    }
}
