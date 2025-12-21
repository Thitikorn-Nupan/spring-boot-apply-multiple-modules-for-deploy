package com.ttknp.applycustomservices.configuration;

import com.ttknp.security.custom.configs.jwt.JwtRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/// Note!! the class name as SecurityConfig not allow because my security service used
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfigCustom {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfigCustom.class);
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfigCustom(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    @Order(2) // Work as setup another module
    public SecurityFilterChain filterChainCustom(HttpSecurity httpSecurity) throws Exception {
        log.info("Configuring filterChainCustom");
        // Test work after run auth micro
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .csrf()
                .disable();
        // All req authen
        httpSecurity.securityMatcher("/api.v2/**") // This chain only matches /server/**
                .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry) -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,"/api.v2/gadget/selectAllOrderBy").permitAll();
                    // Note , hasAuthority(...) will looking to string without prefix!!
                    authorizationManagerRequestMatcherRegistry.anyRequest().hasAuthority("admin");
                }).httpBasic();
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(this.jwtRequestFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }

}