package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.exception.EntityNotFoundException;
import com.adobe.orderapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Product getProductById(@PathVariable("id") int id) throws EntityNotFoundException {
        return  service.getProductById(id);
    }

    // GET http://localhost:8080/api/products/cache/2
    // Accept: application/json
    // SpEL
    // key is id value will be returned data from method
    @Cacheable(value = "productCache", key = "#id")
    @GetMapping("/cache/{id}")
    public Product getProductByCacheId(@PathVariable("id") int id) throws EntityNotFoundException {
        System.out.println("Cache Miss!!!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return  service.getProductById(id);
    }

    // GET http://localhost:8080/api/products/etag/2
    // Accept: application/json
    @GetMapping("/etag/{id}")
    public ResponseEntity<Product> getProductByIdAndEtag(@PathVariable("id") int id) throws EntityNotFoundException {
        Product p =  service.getProductById(id);
        return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
    }

    // POST http://localhost:8080/api/products
    // Accept: application/json
    // Content-type: application/json
    // payload of product
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Product addProduct(@RequestBody @Valid Product p) {
        return  service.addProduct(p);
    }

    // PATCH http://localhost:8080/api/products/1?price=8999.00
    // Accept: application/json
    // Content-type: application/json
    @CachePut(value = "productCache", key="#id")
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestParam("price") double price) throws EntityNotFoundException  {
//        return  service.updateProduct(id, price); // custom JP-QL
        return service.updateBuiltIn(id, price);
    }

    @CacheEvict(value = "productCache", key="#id")
    public String deleteProduct(@PathVariable("id") int id) {
        return  "Product deleted!!!";
    }
}
