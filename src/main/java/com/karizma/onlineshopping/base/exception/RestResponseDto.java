package com.karizma.onlineshopping.base.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class RestResponseDto<T> {

    private boolean successful;
    private T response;
    private ErrorData errorData;

    public static <S> RestResponseDto<S> of(S response) {
        return new RestResponseDto<>(response);
    }

    public static <S> RestResponseDto<S> ok() {
        return new RestResponseDto<>();
    }

    public static RestResponseDto<?> error(ErrorData errorData) {
        return new RestResponseDto<>(errorData);
    }

    public RestResponseDto(T response) {
        this.successful = true;
        this.response = response;
    }

    public RestResponseDto() {
        this.successful = true;
    }

    public RestResponseDto(ErrorData errorData) {
        this.successful = false;
        this.errorData = errorData;
    }

    public RestResponseDto(BaseException exception) {
        this.successful = false;
        this.errorData = ErrorData.builder()
                .errorCode(exception.getCode())
                .message(exception.getMessage())
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorData {
        private int errorCode;
        private String message;
        private Map<String, Object> data;
    }

}
