package com.adobe.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
public class ReactiveApplication {

    public static void main(String[] args) throws  Exception{
        SpringApplication.run(ReactiveApplication.class, args);
        doTask();
    }

    private static void doTask() throws Exception {
        // publisher
        Flux<Long> clockTicks = Flux.interval(Duration.ofSeconds(1));

       // clockTicks.subscribe(tick -> System.out.println("clock 1 " + tick + "s"));

        Thread.sleep(2000);
      //  clockTicks.subscribe(tick -> System.out.println("clock 2 " + tick + "s"));

    }

}
