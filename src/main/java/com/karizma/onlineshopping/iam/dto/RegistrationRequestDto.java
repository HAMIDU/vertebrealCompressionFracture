package com.karizma.onlineshopping.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @NotEmpty
    String mobile;

    private String email;

    private Boolean gender;   //1 means male, 0 is female  , null is other

    private Boolean isEnabled = true;
}
