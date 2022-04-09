package com.zespol11.programowanienzespolowe.order.orderDetails;

import com.zespol11.programowanienzespolowe.order.orderDetails.OrderDetails;
import com.zespol11.programowanienzespolowe.order.orderDetails.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/order/details")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping(path = "/{id}")
    public OrderDetails getOrderDetail(@PathVariable Long id){
     return orderDetailsService.getOrderDetails(id);
    }

    @PostMapping
    public ResponseEntity<?> postOrderDetails(@RequestBody OrderDetails orderDetails){
        orderDetailsService.saveOrder(orderDetails);
        return ResponseEntity.ok("resource added");
    }

    @PutMapping(path = {"/{orderDetailID}"})
    public ResponseEntity<?> updateOrderDetails(@PathVariable("orderDetailID") Long orderDetailID,
                                                @RequestParam(required = false) Long foodItemID,
                                                @RequestParam(required = false) Integer quantity){
        orderDetailsService.paritalUpdate(orderDetailID, foodItemID, quantity);

        return ResponseEntity.ok("resource updated");
    }



    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable Long id){
        orderDetailsService.deleteOrderDetails(id);
        return ResponseEntity.ok("resource deleted");
    }



}
