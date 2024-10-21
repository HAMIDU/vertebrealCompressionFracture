package com.karizma.onlineshopping.aspect;

import com.karizma.onlineshopping.ws.SmsWebService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PurchaseAspect {

    private final SmsWebService smsWebService;

    public PurchaseAspect(SmsWebService smsWebService) {
        this.smsWebService = smsWebService;
    }

    @AfterReturning(pointcut = "execution(* com.karizma.onlineshopping.purchase.service.PurchaseService.purchaseFinalize(..)) ",
            returning = "retVal")
    public void sendSuccessPurchaseSms(Object retVal) {
        log.info("Advise Started ...");
        log.info(retVal.toString());
        smsWebService.sendSms("09124388470", "Successful purchase");
    }
}
