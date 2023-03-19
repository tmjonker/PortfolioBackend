package com.tmjonker.portfoliobackend.security;

import com.tmjonker.portfoliobackend.filters.APIKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${tmjonker.api.key}")
    private String apiKey;

    @Value("${tmjonker.api.header}")
    private String apiHeader;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors()
                .and()
                .authorizeHttpRequests(auth -> {
                    try {
                        auth
                                .anyRequest()
                                .authenticated()
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilterBefore(new APIKeyFilter(apiHeader, apiKey), UsernamePasswordAuthenticationFilter.class);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

        return httpSecurity.build();
    }
}
