package com.zespol11.programowanienzespolowe.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    public List<FoodItem> getFoodItems() {
        return foodItemRepository.findAll();
    }

    public void addNewFoodItem(FoodItem foodItem) {
        Optional<FoodItem> foodItemOptional = foodItemRepository
                .findFoodItemByName(foodItem.getName());

        if(foodItemOptional.isPresent()){
            throw new IllegalStateException("foodItem exists!");
        }

        foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(Long foodItemId) {
        boolean exist = foodItemRepository.existsById(foodItemId);

        if(!exist){
            throw new IllegalStateException("foodItem with id " + foodItemId + "does not exist");
        }

        foodItemRepository.deleteById(foodItemId);
    }

    @Transactional
    public void updateFoodItem(Long foodItemID,
                               String name,
                               Double price) {

        FoodItem foodItem = foodItemRepository.findById(foodItemID)
                .orElseThrow(() -> new IllegalStateException(
                   "student with id" + foodItemID + "does not exist"
                ));

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(foodItem.getName(), name)
        ){
            Optional<FoodItem> foodItemOptional = foodItemRepository
                    .findFoodItemByName(name);

            if(foodItemOptional.isPresent()){
                throw new IllegalStateException("name taken");
            }

            foodItem.setName(name);

        }

        if(price != null &&
                !Objects.equals(foodItem.getPrice(), price)
        ){
            foodItem.setPrice(price);
        }


    }

    public FoodItem getFoodItemById(Long foodItemId) {
       FoodItem foodItem = foodItemRepository.findById(foodItemId)
               .orElseThrow(() -> new IllegalStateException(
                       "foodItem with id " + foodItemId + " does not exist"
               ));

       return foodItem;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        addNewFoodItem(new FoodItem(1L,"Gnochi ze szpinakiem i pomidorami suszonymi", 33.90));
        addNewFoodItem(new FoodItem(2L,"Pieczone żeberka w pikantnej marynacie", 42.90));
        addNewFoodItem(new FoodItem(3L,"Paluchy z parmezanem i sosem pikantnym", 24.90));
        addNewFoodItem(new FoodItem(4L,"Kotlet Schabowy po staropolsku", 37.90));
    }

}
