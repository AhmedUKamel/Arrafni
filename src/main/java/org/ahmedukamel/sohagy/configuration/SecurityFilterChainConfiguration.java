package org.ahmedukamel.sohagy.configuration;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.customizer.AuthorizeHttpRequestsCustomizer;
import org.ahmedukamel.sohagy.customizer.ExceptionHandlingCustomizer;
import org.ahmedukamel.sohagy.customizer.LogoutCustomizer;
import org.ahmedukamel.sohagy.customizer.SessionManagementCustomizer;
import org.ahmedukamel.sohagy.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfiguration {
    final JwtAuthenticationFilter jwtAuthenticationFilter;
    final LogoutCustomizer logoutCustomizer;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(new AuthorizeHttpRequestsCustomizer())
                .sessionManagement(new SessionManagementCustomizer())
                .exceptionHandling(new ExceptionHandlingCustomizer())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logoutCustomizer)
                .build();
    }
}