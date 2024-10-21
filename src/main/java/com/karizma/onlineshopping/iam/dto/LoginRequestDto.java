package com.karizma.onlineshopping.iam.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String ipAddress;

}


