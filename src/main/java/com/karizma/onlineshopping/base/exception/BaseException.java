package com.karizma.onlineshopping.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private String message;

    private Integer code;

    private Integer httpResponseCode;

}
