package com.karizma.onlineshopping.config;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString
public class SecurityContext {

    @NotEmpty
    private Long securityUserId;
}