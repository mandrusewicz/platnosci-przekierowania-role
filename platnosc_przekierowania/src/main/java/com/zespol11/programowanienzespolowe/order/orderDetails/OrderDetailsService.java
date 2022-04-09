package com.zespol11.programowanienzespolowe.order.orderDetails;

import com.zespol11.programowanienzespolowe.food.FoodItem;
import com.zespol11.programowanienzespolowe.food.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final FoodItemRepository foodItemRepository;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository, FoodItemRepository foodItemRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public void saveOrder(OrderDetails orderDetails){
            orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails getOrderDetails(Long id){

        OrderDetails optional = orderDetailsRepository.findById(id).get();



        return optional;
    }

    @Transactional
    public void paritalUpdate(Long id,
                              Long foodItemId,
                              Integer quantity
                              ) {

        OrderDetails orderDetails = orderDetailsRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException(
                        "orderDetails with id " + id + " does not exists"
                ));

        if(foodItemId != null
                && !Objects.equals(orderDetails
                .getFoodItem()
                .getFoodItemId(), foodItemId)){

            FoodItem foodItem = foodItemRepository.findById(foodItemId)
                    .orElseThrow(() -> new IllegalStateException(
                            "foodItem with id " + foodItemId + "does not exist"
                    ));

            orderDetails.setFoodItem(foodItem);
        }

        if(quantity != null
                && !Objects.equals(orderDetails.getQuantity(), quantity)){
            orderDetails.setQuantity(quantity);
        }

    }


    public void deleteOrderDetails(Long id) {
        boolean exist = orderDetailsRepository.existsById(id);

        if(!exist){
            throw new IllegalStateException(
                    "OrderDetails with id " + id + " does not exists");
        }

        orderDetailsRepository.deleteById(id);
    }
}
