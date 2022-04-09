package com.zespol11.programowanienzespolowe.order.orderDetails;

import com.zespol11.programowanienzespolowe.order.orderDetails.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query("SELECT o FROM OrderDetails o WHERE o.orderMaster.orderMasterId = :id")
    List<OrderDetails> findOrderDetails(Long id);
}
