package org.ahmedukamel.sohagy;

import org.ahmedukamel.sohagy.model.User;
import org.ahmedukamel.sohagy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class Runner {
//    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            ZonedDateTime registration = repository.findById(1L).orElseGet(User::new).getRegistration();
            System.out.println(registration);
            System.out.println(registration.getZone());
        };
    }
}
