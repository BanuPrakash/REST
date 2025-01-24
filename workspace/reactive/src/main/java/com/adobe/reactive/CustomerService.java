package com.adobe.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private  final CustomerDao dao;
    public List<Customer> getCustomers() {
        long start = System.currentTimeMillis();
            List<Customer> customers = dao.getCustomers(); // blocking
        long end = System.currentTimeMillis();
        System.out.println("time blocking code: " + ( end - start) + " ms");
        return customers;
    }

    public Flux<Customer> getCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers = dao.getCustomerStream(); // non blocking
        long end = System.currentTimeMillis();
        System.out.println("time non-blocking code: " + ( end - start) + " ms");
        return customers; // return publisher
    }
}
