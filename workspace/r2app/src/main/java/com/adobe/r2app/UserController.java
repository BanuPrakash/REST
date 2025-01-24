package com.adobe.r2app;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    final UserRepository userRepository;
    @GetMapping(value = "users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<UserEntity> getUsers() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<UserEntity> users =  userRepository.findAll();

        Flux<UserEntity> zipped = Flux.zip(users, interval, (key, value) -> key);
        return zipped;
//        return users;
    }
}
