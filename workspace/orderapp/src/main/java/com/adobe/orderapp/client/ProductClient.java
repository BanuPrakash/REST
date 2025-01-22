package com.adobe.orderapp.client;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.exception.EntityNotFoundException;
import com.adobe.orderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductClient implements CommandLineRunner {
    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        addProducts();
        listProducts();
        listByRange();

        updateProduct();
    }

    private void updateProduct() throws EntityNotFoundException {
        Product p = orderService.updateProduct(2, 5200.00);
        System.out.println("After update");
        System.out.println(p);
    }

    private void listByRange() {
        System.out.println("List by Range...");
        List<Product> products = orderService.byRange(100, 10000);
        for(Product p : products) {
            System.out.println(p);
        }
    }

    private void listProducts() {
        List<Product> products = orderService.getProducts();
        for(Product p : products) {
            System.out.println(p);
        }
    }

    private void addProducts() {
        if(orderService.getProductCount() == 0) {
//            orderService.addProduct(new Product(0, "iPhone 16", 98000.00, 100));
//
//            orderService.addProduct(new Product(0, "Wacom", 4500.99,100));
        }
    }
}
