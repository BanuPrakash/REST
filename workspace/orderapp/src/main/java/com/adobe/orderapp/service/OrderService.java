package com.adobe.orderapp.service;

import com.adobe.orderapp.aop.Tx;
import com.adobe.orderapp.entity.LineItem;
import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.repo.OrderRepository;
import com.adobe.orderapp.repo.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository; // created class is wired
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    // atomic in nature
    // order and line items should be saved or rollback
    // when we purchase a product, inventory should reduce quantity
    // payload looks like
    // purchasing one LG AC and 2 iPhone
    /*
        {
            "customer": { "email": "rita@adobe.com"},
            "items": [
                {"product": {"id": 3}, "qty": 1},
                {"product": {"id": 1}, "qty": 2}
            ]
        }

        lineitem amount has to be calcluated,
        order date is system date, total is computed
     */
    @Transactional
    public String placeOrder(Order order) {
        double total = 0.0;
        List<LineItem> items = order.getItems();
        for(LineItem item: items) {
            Product p = productRepository.findById(item.getProduct().getId()).get();
            if(p.getQuantity() < item.getQty()) {
                throw  new IllegalArgumentException("Product not in Stock!!!");
            }
            // update the quantity
            // any changes to entity in @Transactional will be flushed to DB
            // no need to explicitly call update SQL
            // DIRTY CHECKING, Product p become dirty --> flush changes to DB
            p.setQuantity(p.getQuantity() - item.getQty()); // LG 99, iPhone 98

            item.setAmount(p.getPrice() * item.getQty()); // add discount, tax
            total += item.getAmount();
        }
        order.setTotal(total);
        orderRepository.save(order); // saves order, and it's line items --> Cascade
        return "Order Placed!!!";
    }

    public long getProductCount() {
        return productRepository.count(); // no of products in table
    }

    @Tx
//    @Secured
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
//        Optional<Product> opt = productRepository.findById(id);
//        if(opt.isPresent()) {
//            return opt.get();
//        }
        return productRepository.findById(id).get();
    }

    public Product addProduct(Product p) {
        return  productRepository.save(p);
    }

    public List<Product> byRange(double low, double high) {
        return  productRepository.getByRange(low, high);
    }

    @Transactional
    public Product updateProduct(int id, double price) {
        productRepository.updateProduct(id, price);
        return  getProductById(id);
    }
}
