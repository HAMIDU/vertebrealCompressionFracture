package com.karizma.onlineshopping.config;

import com.auth0.jwt.interfaces.Claim;
import com.karizma.onlineshopping.base.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Order(1)
@Slf4j
public class RequestFilter extends OncePerRequestFilter {

    private final SecurityContext securityContext;
    private final JWTUtils jwtUtils;


    public RequestFilter(SecurityContext securityContext, JWTUtils jwtUtils) {
        this.securityContext = securityContext;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getHeader(AUTHORIZATION) != null) {
            logger.info("Request Needs AUTHORIZATION ...");
            jwtUtils.validate(httpServletRequest.getHeader(AUTHORIZATION));
            Map<String, Claim> claims = jwtUtils.getAllClaims(httpServletRequest.getHeader(AUTHORIZATION));
            securityContext.setSecurityUserId(claims.get("securityUserId") != null ? claims.get("securityUserId").asLong() : null);
            var authorities = claims.get("role").asList(String.class).stream().
                    map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            claims.entrySet().stream().forEach(e -> logger.info("Token Payload:" + e));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
