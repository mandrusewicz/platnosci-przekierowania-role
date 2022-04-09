package com.zespol11.programowanienzespolowe.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    @Query("SELECT f FROM FoodItem f WHERE f.name = ?1")
    Optional<FoodItem> findFoodItemByName(String name);
}
