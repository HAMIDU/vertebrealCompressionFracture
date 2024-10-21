package com.karizma.onlineshopping.ws;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@Slf4j
public class DeploymentSmsWebService implements SmsWebService {

    @Override
    public void sendSms(String mobileNo, String message) {
        log.info("prod environment, SMS Sending...{} {}", mobileNo, message);
    }
}
