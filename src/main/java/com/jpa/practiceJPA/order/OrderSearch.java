package com.jpa.practiceJPA.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static com.jpa.practiceJPA.order.OrderSpec.memberNameLike;
import static com.jpa.practiceJPA.order.OrderSpec.orderStatusEq;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public Specification<Order> toSpecification() {
        return Specification.where(memberNameLike(memberName)
                .and(orderStatusEq(orderStatus)));
    }

}
