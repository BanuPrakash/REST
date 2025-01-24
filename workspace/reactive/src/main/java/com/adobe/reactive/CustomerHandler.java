package com.adobe.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerHandler {
    private final CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        Flux<Customer> list = customerDao.getCustomerStream();
        return ServerResponse.ok().body(list, Customer.class);
    }


    public Mono<ServerResponse> loadCustomerStream(ServerRequest request) {
        Flux<Customer> list = customerDao.getCustomerStream();
        return ServerResponse.ok().
                contentType(MediaType.TEXT_EVENT_STREAM) // SSE
        .body(list, Customer.class);
    }

    // http://localhost:8080/customers/3
    public Mono<ServerResponse> findCustomers(ServerRequest request) {
        int id= Integer.valueOf(request.pathVariable("input"));
        Mono<Customer> customer = customerDao.getCustomerStream()
                .filter(c -> c.getId() == id).next();
        return ServerResponse.ok().body(customer, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class); // payload
        Mono<String> response =
                customerMono.map(dto -> dto.getId() + " : " + dto.getName());

        return ServerResponse.ok().body(response, String.class);
    }

}
