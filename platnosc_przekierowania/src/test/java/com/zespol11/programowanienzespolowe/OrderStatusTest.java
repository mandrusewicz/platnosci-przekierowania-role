package com.zespol11.programowanienzespolowe;

import com.zespol11.programowanienzespolowe.order.EnumStatus;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasterRepository;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasterService;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class OrderStatusTest {


    @Test
    public void orderMasterStatusTest(){
        OrderMasters orderMasters = new OrderMasters();

        orderMasters.setStatus(EnumStatus.ORDERED);

        Assertions.assertTrue(orderMasters.getStatus().isOrdered());
        Assertions.assertFalse(orderMasters.getStatus().isDelivered());




    }

}
