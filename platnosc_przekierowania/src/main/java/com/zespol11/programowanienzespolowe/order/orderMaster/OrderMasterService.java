package com.zespol11.programowanienzespolowe.order.orderMaster;

import com.zespol11.programowanienzespolowe.food.FoodItem;
import com.zespol11.programowanienzespolowe.food.FoodItemRepository;
import com.zespol11.programowanienzespolowe.order.EnumStatus;
import com.zespol11.programowanienzespolowe.order.orderDetails.OrderDetails;
import com.zespol11.programowanienzespolowe.order.orderDetails.OrderDetailsRepository;
import com.zespol11.programowanienzespolowe.userRegistration.appuser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderMasterService {

    private final OrderMasterRepository orderMasterRepository;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderMasterService(OrderMasterRepository orderMasterRepository, FoodItemRepository foodItemRepository, UserRepository userRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderMasterRepository = orderMasterRepository;
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderMasters> getOrders(){
        return orderMasterRepository.findAll();
    }

    public void saveOrder(OrderMasters orderMasters){

        boolean exist = userRepository
                .existsById(orderMasters.getCustomer().getCustomerId());

        if(!exist){
            throw new IllegalStateException(
                    "user with id " + orderMasters.getCustomer().getCustomerId() + " does not exist"
            );
        }



        for(OrderDetails o: orderMasters.getOrderDetails()){

            Long foodItemId = o.getFoodItemID();
            exist = foodItemRepository.
                    existsById(foodItemId);

            if(!exist){
                throw new IllegalStateException("foodItem with id " + foodItemId + " does not exist");
            }

            Optional<FoodItem> foodItem = foodItemRepository.findById(foodItemId);

            o.setFoodItem(foodItem.get());
        }

        EnumStatus status = EnumStatus.ORDERED;

        orderMasters.setStatus(status);

        orderMasterRepository.save(orderMasters);
    }

    public Optional<OrderMasters> getOrderById(Long id) {
        boolean exist = orderMasterRepository.existsById(id);

        if(!exist){
            throw new IllegalStateException(
                    "Order with id " + id + " does not exist!"
            );
        }

        return orderMasterRepository.findById(id);
    }


    @Transactional
    public void partialUpdateOrder(Long id,
                                   OrderMasters oM) {
        OrderMasters orderMasters = orderMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "orderMaster with id " + id + "does not exist"
                ));

        if(oM.getOrderNumber() != null
                && !Objects.equals(oM.getOrderNumber(), orderMasters.getOrderNumber())){
            orderMasters.setOrderNumber(oM.getOrderNumber());
        }


        orderMasterRepository.save(orderMasters);

    }

    public void deleteOrder(Long id) {
        boolean exist = orderMasterRepository.existsById(id);

        if(!exist){
            throw new IllegalStateException(
                    "Order with id " + id + " does not exsists"
            );
        }

        orderMasterRepository.deleteById(id);
    }

    public List<OrderMasters> getOrdersWithStatus(EnumStatus e) {
        Optional<List<OrderMasters>> optionalOrderMasters = orderMasterRepository.findByStatus(e);

        if(optionalOrderMasters.isEmpty()){
            throw new IllegalStateException(
              "no orders with status " + e
            );
        }

        return optionalOrderMasters.get();
    }

    @Transactional
    public void changeStatus(Long id, EnumStatus e){

        OrderMasters orderMasters = orderMasterRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(
                        "orderMaster with id " + id + "does not exist"
                ));

        orderMasters.setStatus(e);

    }
}
