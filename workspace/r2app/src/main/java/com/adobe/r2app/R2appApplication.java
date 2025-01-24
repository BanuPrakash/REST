package com.adobe.r2app;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableR2dbcRepositories
@EnableScheduling
@SpringBootApplication
public class R2appApplication {
    @Autowired
    UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(R2appApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    void addUser() {
        System.out.println("schedule");
        userService.createUser().log().then().subscribe();
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return ConnectionFactories.get("r2dbc:h2:mem://./testdb");
//    }
}