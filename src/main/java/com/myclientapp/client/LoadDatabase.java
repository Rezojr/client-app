package com.myclientapp.client;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository clientRepository) {
        return args -> {
            clientRepository.save(new Client("Bilbo", "Baggins", 21, "Test@gmail.com", "123123213", 1));
            clientRepository.save(new Client("Frodo", "Baggins", 31, "lost@gmail.com", "555666777", 1));
        };
    }

}
