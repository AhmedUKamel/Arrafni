package org.ahmedukamel.arrafni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ArrafniApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ArrafniApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ArrafniApplication.class);
    }
}