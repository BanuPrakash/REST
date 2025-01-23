package com.adobe.springdatarest.api;

import com.adobe.springdatarest.dao.ProductRepository;
import com.adobe.springdatarest.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@BasePathAwareController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(path = "products", method = RequestMethod.GET)
    public @ResponseBody List<Product> get() {
        System.out.println("Called custom API...");
        List<Product> products = productRepository.findAll();
        // traverse, add custom links WebMvcLinkBuilder, ....
        return products.subList(2,3);
    }
}
