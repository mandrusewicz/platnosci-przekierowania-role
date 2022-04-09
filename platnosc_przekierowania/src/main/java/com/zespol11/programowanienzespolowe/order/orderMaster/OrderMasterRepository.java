package com.zespol11.programowanienzespolowe.order.orderMaster;

import com.zespol11.programowanienzespolowe.order.EnumStatus;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMasters, Long> {

    @Query("SELECT o FROM order_masters o WHERE o.status = ?1")
    Optional<List<OrderMasters>> findByStatus(EnumStatus e);
}
