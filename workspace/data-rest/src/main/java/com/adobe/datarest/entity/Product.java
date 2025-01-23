package com.adobe.datarest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="products")
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private int id;

    @NotBlank(message = "{NotBlank.product.name}")
//    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 10, message = "{Min.product.price}")
//    @Min(value=10, message = "Price ${validatedValue} should be equal or more than {value}")
    private double price;

    @Min(value = 1, message = "{Min.product.quantity}")
//    @Min(value=1, message = "Quantity ${validatedValue} should be equal or more than {value}")
    private int quantity;

    @Version
    private int version;

//    public Product() {
//    }
//
//    public Product(int id, String name, double price, int quantity) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                ", quantity=" + quantity +
//                '}';
//    }
}
