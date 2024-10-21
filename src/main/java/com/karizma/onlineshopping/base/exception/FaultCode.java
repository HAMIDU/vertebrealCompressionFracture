package com.karizma.onlineshopping.base.exception;

public enum FaultCode {

    INTERNAL_SERVER_ERROR(9000, "Internal Server Error", 500),
    INVALID_TOKEN(9001, "Invalid token", 403),
    TOKEN_REQUIRED(9002, "Token Required", 403),
    HAS_NOT_ACCESS(9003, "Has Not Access", 403),
    SERVICE_NOT_AVAILABLE(9004, "Service Not Available", 503),

    DATA_SIGN_FAILED(9005, "data sign failed", 603),

    DATA_INTEGRITY_VIOLATION_EXCEPTION(9006, "constraint violation exception", 400),
    INVALID_CREDENTIALS(9007, "incorrect password", 400),
    USER_IS_NOT_ENABLED(9008, "user is not enabled", 400),


    NO_CUSTOMER_FOUND(9030, "customer not found", 402),
    NOT_FOUND_PRODUCT_DETAILS(9031, "not found product details", 402),
    NOT_FOUND_ACTIVE_CATEGORY(9032, "not_found_active_category", 402),
    NOT_FOUND_ACTIVE_PRODUCT(9033, "not found active product", 402),
    PRODUCT_NOT_EXISTS_ENOUGH(9034, "no enough product exists", 402),
    TOTAL_AMOUNT_IS_LESS_THAN_MINIMUM_VALID_PRICE(9035, "total mount is less than minimum valid price", 402),
    COULD_NOT_PURCHASE_NOW(9036, "could not purchase now", 402),
    INVALID_PURCHASE(9037, "invalid purchase", 402),
    INVALID_BASKET(9038, "invalid basket", 402),


    USERNAME_DECLARED_BEFORE(9021, "username declared before", 422),
    MOBILE_NUMBER_REGISTERED_BEFORE(9022, "customer mobile number registered before", 422),
    PASSWORD_CONTAIN_USERNAME(9023, "password must not contain username.", 422),
    PASSWORD_PATTERN_INVALID(9024, "password pattern invalid.", 422),
    PASSWORD_TOO_LONG_ERROR(9025, "password is too long", 422);


    private final Integer code;

    private final String message;

    private final Integer httpResponseCode;

    FaultCode(Integer code, String message, Integer httpResponseCode) {
        this.code = code;
        this.message = message;
        this.httpResponseCode = httpResponseCode;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }
}
