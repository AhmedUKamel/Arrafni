package org.ahmedukamel.sohagy.customizer;

import org.ahmedukamel.sohagy.handler.CustomLogoutHandler;
import org.ahmedukamel.sohagy.handler.logout.CustomLogoutSuccessHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutCustomizer implements Customizer<LogoutConfigurer<HttpSecurity>> {
    final LogoutHandler logoutHandler;
    final LogoutSuccessHandler logoutSuccessHandler;

    public LogoutCustomizer(CustomLogoutHandler logoutHandler, CustomLogoutSuccessHandler logoutSuccessHandler) {
        this.logoutHandler = logoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    public void customize(LogoutConfigurer<HttpSecurity> configurer) {
        configurer.logoutSuccessHandler(logoutSuccessHandler);
        configurer.addLogoutHandler(logoutHandler);
        configurer.clearAuthentication(true);
        configurer.logoutUrl("/logout");
    }
}