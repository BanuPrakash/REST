package com.adobe.springdatarest.dao;

import com.adobe.springdatarest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByIdIn(List<Integer> ids);

    // select * from products where quantity = ?
    List<Product> findByQuantity(int quantity); // Projections
    // select * from products where price between ? and ?
    List<Product> findByPriceBetween(double low, double high);

    List<Product> findByNameOrPrice(String name, double price);

    List<Product> findByNameAndPrice(String name, double price);

    //
//    @Query(value = "select * from products where price >= :l and price <= :q",
//            nativeQuery = true)
    @Query("from Product  where price >= :l and price <= :h") // JP-QL
    List<Product> getByRange(@Param("l") double low, @Param("h") double high);

    @Modifying
    @Query("update Product  set price = :pr where id  = :id") // JP-QL
//    @Query(value = "update products  set price = :pr where id  = :id", nativeQuery = true)
    void updateProduct(@Param("id") int id, @Param("pr") double price);
}

