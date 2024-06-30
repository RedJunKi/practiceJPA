package com.jpa.practiceJPA.delivery;

import com.jpa.practiceJPA.order.Order;
import com.jpa.practiceJPA.order.OrderStatus;
import com.jpa.practiceJPA.valueobject.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;


    public Delivery(Address address) {
        this.address = address;
    }
}
