package org.ahmedukamel.arrafni;

import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner {
//    @Bean
    CommandLineRunner commandLineRunner(BusinessRepository repository) {
        return args -> {
            var businesses = repository.searchNearestBusinessWithPagination(
                    "a",
                    28.091562033721903,
                    30.76091987768122,
                    100L,
                    0L
            );

            businesses.forEach((item) -> {
                Business business = (Business) item[0];
                double distance = (double) item[1];
                System.out.printf("%s: %f\n", business.getDescription(), distance);
            });
        };
    }
}
