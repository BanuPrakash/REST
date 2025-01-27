package com.adobe.orderapp.repo;

import com.adobe.orderapp.dto.ReportDTO;
import com.adobe.orderapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

//    @Query(value = "select o.oid,o.order_date, c.first_name, " +
//            "c.email, o.total from orders o left outer join on customers c " +
//            "where orders.customer_fk = c.email", nativeQuery = true)
//    @Query("select o.oid, o.orderDate, c.firstName, c.email from Order o join o.customer c")
//    List<Object[]> getReport();

    @Query("select new com.adobe.orderapp.dto.ReportDTO(o.oid, o.orderDate, c.firstName, c.email) from Order o join o.customer c")
    List<ReportDTO> getReport();
}
