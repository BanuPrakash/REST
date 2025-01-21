package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 201
    public String createOrder(@RequestBody Order order){
        return service.placeOrder(order);
    }

    @GetMapping()
    public List<Order> getOrders() {
        return  service.getOrders();
    }
}
