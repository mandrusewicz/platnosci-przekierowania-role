package com.zespol11.programowanienzespolowe.order.orderMaster;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zespol11.programowanienzespolowe.order.EnumStatus;
import com.zespol11.programowanienzespolowe.order.orderDetails.OrderDetails;

import com.zespol11.programowanienzespolowe.userRegistration.appuser.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.List;


@Entity(name = "order_masters")
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderMasterId"
)
public class OrderMasters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderMasterId;

    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ordermaster_id")
    private List<OrderDetails> orderDetails;

    private String pMethod;

    private EnumStatus status;









}
