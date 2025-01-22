package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService service; // Constructor DI, no need for @Autowired

    // GET http://localhost:8080/api/products
    // GET http://localhost:8080/api/products?low=1000&high=50000
    // Accept: application/json
    @GetMapping()
    public List<Product> getProducts(@RequestParam(name = "low", defaultValue = "0.0") double low,
                                     @RequestParam(name="high", defaultValue = "0.0") double high) {
        if(low == 0.0 && high == 0.0) {
            return service.getProducts();
        } else {
            return service.byRange(low, high);
        }
    }

    // GET http://localhost:8080/api/products/2
    // Accept: application/json
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        return  service.getProductById(id);
    }


    // POST http://localhost:8080/api/products
    // Accept: application/json
    // Content-type: application/json
    // payload of product
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Product addProduct(@RequestBody Product p) {
        return  service.addProduct(p);
    }

    // PATCH http://localhost:8080/api/products/1?price=8999.00
    // Accept: application/json
    // Content-type: application/json
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestParam("price") double price) {
        return  service.updateProduct(id, price);
    }
}
