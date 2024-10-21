package com.karizma.onlineshopping.base.exception;


public class CoreException extends BaseException {

    private static final long serialVersionUID = 173832310933353710L;

    public CoreException(FaultCode faultCode) {
        super(faultCode.getMessage(), faultCode.getCode(), faultCode.getHttpResponseCode());
    }
}

