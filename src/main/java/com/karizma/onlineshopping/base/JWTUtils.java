package com.karizma.onlineshopping.base;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;;
import com.karizma.onlineshopping.base.exception.CoreException;
import com.karizma.onlineshopping.base.exception.FaultCode;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.removeStart;


@Component
public class JWTUtils {

    private final OnlineShoppingPropertiesWrapper onlineShoppingPropertiesWrapper;

    public JWTUtils(OnlineShoppingPropertiesWrapper onlineShoppingPropertiesWrapper) {
        this.onlineShoppingPropertiesWrapper = onlineShoppingPropertiesWrapper;
    }


    public Map<String, Claim> getAllClaims(String token) {
        String jwt = removePart(token);
        return createVerifier(Algorithm.HMAC512(onlineShoppingPropertiesWrapper.getSecretKey())).verify(jwt).getClaims();
    }

    public String generateToken(Map<String, Object> claims, String subject, Integer expirationTime) {
        return JWT.create()
                .withPayload(claims)
                .withSubject(subject)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(onlineShoppingPropertiesWrapper.getSecretKey()));
    }


    private String removePart(String token) {
        Optional<String> jwt = Optional.ofNullable(token)
                .map(value -> removeStart(value, "Bearer"))
                .map(String::trim);
        return jwt.orElseThrow(() -> {
            throw new CoreException(FaultCode.INVALID_TOKEN);
        });
    }

    public boolean validate(String token) {
        String jwt = removePart(token);
        try {
            createVerifier(Algorithm.HMAC512(onlineShoppingPropertiesWrapper.getSecretKey())).verify(jwt);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public JWTVerifier createVerifier(Algorithm algorithm) {
        return JWT.require(algorithm).build();
    }

}
