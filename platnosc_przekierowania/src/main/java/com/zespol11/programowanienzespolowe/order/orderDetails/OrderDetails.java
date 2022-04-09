package com.zespol11.programowanienzespolowe.order.orderDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zespol11.programowanienzespolowe.food.FoodItem;
import com.zespol11.programowanienzespolowe.order.orderMaster.OrderMasters;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import javax.persistence.*;
import java.util.List;



@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderDetailId"
)
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordermaster_id")
    @JsonIgnore
    private OrderMasters orderMaster;

    @ManyToOne
    @JoinColumn(name = "fooditem_id")
    private FoodItem foodItem;

    private Long foodItemID;

    private Integer quantity;


}
