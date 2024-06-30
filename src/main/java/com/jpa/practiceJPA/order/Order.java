package com.jpa.practiceJPA.order;

import com.jpa.practiceJPA.BaseEntity;
import com.jpa.practiceJPA.delivery.Delivery;
import com.jpa.practiceJPA.delivery.DeliveryStatus;
import com.jpa.practiceJPA.member.Member;
import com.jpa.practiceJPA.joinentity.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    public void setMember(Member member) {

        if (this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        Arrays.stream(orderItems).forEach(order::addOrderItem);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new RuntimeException("이미 배송완료 된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        orderItems.stream().forEach(OrderItem::cancel);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
