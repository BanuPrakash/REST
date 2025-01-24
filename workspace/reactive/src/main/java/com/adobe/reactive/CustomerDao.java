package com.adobe.reactive;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {
    private static void sleepEx(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // blocking
    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1, 10)
                .peek(CustomerDao::sleepEx)
                .peek(i -> System.out.println("processing count : " + i))
                .mapToObj(i-> new Customer(i, "Customer : " + i))
                .collect(Collectors.toList());
    }

    // non blocking
    public Flux<Customer> getCustomerStream() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Processing count in stream : " + i))
                .map(i -> new Customer(i, "Customer " + i));
    }
}
