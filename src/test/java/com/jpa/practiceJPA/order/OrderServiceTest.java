package com.jpa.practiceJPA.order;

import com.jpa.practiceJPA.item.Book;
import com.jpa.practiceJPA.item.Item;
import com.jpa.practiceJPA.member.Member;
import com.jpa.practiceJPA.valueobject.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    // 상품 주문 기능
    // 상품 주문시 재고수량 초과하면 안됨
    // 주문 취소 기능

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() {
        //given
        Member member = createMember();
        Item item = createBook("시골JPA", 10000, 10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(20000);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        Item item = createBook("시골JPA", 10000, 10);
        int orderCount = 11;

        //when


        //then
        assertThatThrownBy(() ->
                orderService.order(member.getId(), item.getId(), orderCount)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문취소() {
        //given
        Member member = createMember();
        Item item = createBook("시골JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);


        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

}