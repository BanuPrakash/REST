package com.adobe.r2app;

import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Transactional
    public Mono<UserEntity> createUser() {
        System.out.println("Called schedule...");
        Faker faker = new Faker();
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton
        String address = faker.address().streetAddress();
        return userRepository.save(new UserEntity(firstName, lastName, address));
    }
}
