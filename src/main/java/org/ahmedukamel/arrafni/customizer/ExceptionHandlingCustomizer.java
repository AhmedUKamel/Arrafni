package org.ahmedukamel.arrafni.customizer;

import org.ahmedukamel.arrafni.handler.CustomAuthenticationEntryPointHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

public class ExceptionHandlingCustomizer implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        configurer.authenticationEntryPoint(new CustomAuthenticationEntryPointHandler());
    }
}