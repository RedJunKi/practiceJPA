package com.jpa.practiceJPA.order;

import com.jpa.practiceJPA.delivery.Delivery;
import com.jpa.practiceJPA.item.Item;
import com.jpa.practiceJPA.item.ItemService;
import com.jpa.practiceJPA.joinentity.OrderItem;
import com.jpa.practiceJPA.member.Member;
import com.jpa.practiceJPA.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemService itemService;

    public Long order(Long memberId, Long itemId, int count) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        Item findItem = itemService.findOne(itemId);

        Delivery delivery = new Delivery(findMember.getAddress());
        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);
        Order order = Order.createOrder(findMember, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch.toSpecification());
    }
}
