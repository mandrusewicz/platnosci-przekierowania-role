package com.zespol11.programowanienzespolowe.order.orderMaster;

import com.zespol11.programowanienzespolowe.order.EnumStatus;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasterService;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {

    private final OrderMasterService orderMasterService;

    @Autowired
    public OrderController(OrderMasterService orderMasterService) {
        this.orderMasterService = orderMasterService;
    }

    @GetMapping
    public List<OrderMasters> getOrders(){
        return orderMasterService.getOrders();
    }

    @GetMapping(path = "/{id}")
    public Optional<OrderMasters> getOrder(@PathVariable Long id){
        return orderMasterService.getOrderById(id);
    }

    @GetMapping("/ready")
    public List<OrderMasters> getReady(){
        return orderMasterService.getOrdersWithStatus(EnumStatus.READY);
    }

    @GetMapping("/ordered")
    public List<OrderMasters> getOrdered(){
        return orderMasterService.getOrdersWithStatus(EnumStatus.ORDERED);
    }

    @GetMapping("/preparing")
    public List<OrderMasters> getPreparing(){return orderMasterService.getOrdersWithStatus(EnumStatus.PREPARING);}

    @PostMapping
    public void postOrder(@RequestBody OrderMasters orderMasters){
        orderMasterService.saveOrder(orderMasters);
    }

    @PutMapping(path= "/change-status-to-ready/{id}")
    public void changeStatusToReady(@PathVariable Long id){
        orderMasterService.changeStatus(id, EnumStatus.READY);
    }

    @PutMapping(path= "/change-status-to-preparing/{id}")
    public void changeStatusToPreparing(@PathVariable Long id){
        orderMasterService.changeStatus(id, EnumStatus.PREPARING);
    }

    @PutMapping(path= "/change-status-to-delivered/{id}")
    public void changeStatusToDelivered(@PathVariable Long id){
        orderMasterService.changeStatus(id, EnumStatus.DELIVERED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderMasterService.deleteOrder(id);
        return ResponseEntity.ok("resource deleted");
    }




}
