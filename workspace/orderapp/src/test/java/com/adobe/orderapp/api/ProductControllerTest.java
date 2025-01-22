package com.adobe.orderapp.api;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @MockitoBean
    private OrderService service;

    @Autowired
    private MockMvc mvc;


    @Test
    public void getAllProductsTest() throws Exception{
        // mock data
        List<Product> productList = Arrays.asList(
                Product.builder().id(11).name("A").price(123.00).quantity(100).build(),
                Product.builder().id(12).name("B").price(345.00).quantity(100).build()
        );

        // mock the Service API
        when(service.getProducts()).thenReturn(productList);

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("A")))
                .andExpect(jsonPath("$[1].name", is("B")));

        verify(service, times(1)).getProducts();
    }

    @Test
    public void addProductTest() throws  Exception {
        // valid data
        // client creates a Product
        Product p = Product.builder().name("P1").price(310.00).quantity(110).build();
        // convert to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p); // json of Product

        // mocking service
        when(service.addProduct(Mockito.any(Product.class)))
                .thenReturn(Mockito.any(Product.class));

        mvc.perform(post("/api/products")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).addProduct(Mockito.any(Product.class));
    }

    @Test
    public void addInvalidProductTest() throws  Exception {
        // valid data
        // client creates a Product
        Product p = Product.builder().name("").price(3.00).quantity(0).build();
        // convert to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p); // json of Product

        // no need to mocking service
//        when(service.addProduct(Mockito.any(Product.class)))
//                .thenReturn(Mockito.any(Product.class));

        mvc.perform(post("/api/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors",hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("Name is required")));

            verifyNoInteractions(service);
         }
}
