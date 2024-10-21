
package com.kariza.onlineshopping.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kariza.onlineshopping.AbstractBaseIT;
import com.kariza.onlineshopping.HttpTestUtil;
import com.karizma.onlineshopping.base.exception.FaultCode;
import com.karizma.onlineshopping.base.exception.RestResponseDto;
import com.karizma.onlineshopping.iam.dto.RegistrationRequestDto;
import com.karizma.onlineshopping.iam.entity.SecurityUser;
import com.karizma.onlineshopping.iam.entity.UserProfile;
import com.karizma.onlineshopping.iam.repository.SecurityUserRepository;
import com.karizma.onlineshopping.iam.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IAMControllerTest extends AbstractBaseIT {

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RegistrationRequestDto registrationRequestDto;

    @BeforeEach
    public void beforeEach() {
        dataCleaner.deleteAllData();
        registrationRequestDto = new registrationRequestDto();
        setupUser();
        setUpUserProfile();
    }

    private void setUpUserProfile() {
        userProfileRepository.save(UserProfile.builder().id(1L).mobile("0989121111111").firstName("testName")
                .lastName("testFamily")
                .build());
    }

    private void setupUser() {
        securityUserRepository.save(SecurityUser.builder()
                .username("john")
                .isEnabled(true)
                .password("pass".getBytes())
                .registrationDate(LocalDate.now())
                .build());
    }

    @Test
    void register_duplicateUsername_throwsException() throws Exception {
        //arrange
        registrationRequestDto.setPassword("pass");
        registrationRequestDto.setUsername("john");

        // act
        var mockRq = getMockedRq(registrationRequestDto);

        var perform = mvc.perform(mockRq);

        // assert
        perform.andExpect(status().isUnprocessableEntity());

        var error = getErrorResponse(perform);
        assertEquals(FaultCode.USERNAME_DECLARED_BEFORE.getCode(), error.getErrorCode());
    }

    @Test
    void register_duplicatePhoneNumber_throwsException() throws Exception {
        //arrange
        registrationRequestDto.setPassword("pass");
        registrationRequestDto.setUsername("userTest");
        registrationRequestDto.setMobile("09121111111");

        // act
        var mockRq = getMockedRq(registrationRequestDto);

        var perform = mvc.perform(mockRq);

        // assert
        perform.andExpect(status().isUnprocessableEntity());

        var error = getErrorResponse(perform);
        assertEquals(FaultCode.MOBILE_NUMBER_REGISTERED_BEFORE.getCode(), error.getErrorCode());
    }

    @Test
    void register_passwordContainsUsername_throwsException() throws Exception {
        //arrange
        registrationRequestDto.setPassword("usernamePassword");
        registrationRequestDto.setUsername("username");

        // act
        var mockRq = getMockedRq(registrationRequestDto);

        var perform = mvc.perform(mockRq);

        // assert
        perform.andExpect(status().isUnprocessableEntity());

        var error = getErrorResponse(perform);
        assertEquals(FaultCode.PASSWORD_CONTAIN_USERNAME.getCode(), error.getErrorCode());
    }

    @Test
    void register_passwordTooLong_throwsException() throws Exception {
        //arrange
        registrationRequestDto.setPassword("usernamePassword");
        registrationRequestDto.setUsername("kevin");

        // act
        var mockRq = getMockedRq(registrationRequestDto);

        var perform = mvc.perform(mockRq);

        // assert
        perform.andExpect(status().isUnprocessableEntity());

        var error = getErrorResponse(perform);
        assertEquals(FaultCode.PASSWORD_TOO_LONG_ERROR.getCode(), error.getErrorCode());
    }

    @Test
    void register_passwordInvalidPattern_throwsException() throws Exception {
        //arrange
        registrationRequestDto.setPassword("pass");
        registrationRequestDto.setUsername("kevin");

        // act
        var mockRq = getMockedRq(registrationRequestDto);

        var perform = mvc.perform(mockRq);

        // assert
        perform.andExpect(status().isUnprocessableEntity());

        var error = getErrorResponse(perform);
        assertEquals(FaultCode.PASSWORD_PATTERN_INVALID.getCode(), error.getErrorCode());
    }

    @Test
    void register_happyFlow() throws Exception {
        //arrange
        registrationRequestDto.setPassword("Pass@123");
        registrationRequestDto.setUsername("kevin");

        // act
        var mockRq = getMockedRq(registrationRequestDto);

        var perform = mvc.perform(mockRq);

        // assert
        perform.andExpect(status().isOk());

        var securityUser = securityUserRepository.findSecurityUserByUsernameAndAndIsEnabled("kevin",
                true);
        assertNotNull(securityUser);
        assertTrue(securityUser.getIsEnabled());

        var userProfile = userProfileRepository.findById(securityUser.getId());
        assertEquals(registrationRequestDto.getMobile(), userProfile.get().getMobile());
    }

    private MockHttpServletRequestBuilder getMockedRq(Object content) throws Exception {
        return HttpTestUtil.getBaseJsonMvcBuilder(HttpMethod.POST, "/iam/register")
                .content(objectMapper.writeValueAsBytes(content));
    }

    private RestResponseDto.ErrorData getErrorResponse(ResultActions resultActions) throws Exception {
        return objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsByteArray(),
                new TypeReference<RestResponseDto<RestResponseDto.ErrorData>>() {
                }
        ).getErrorData();
    }
}

