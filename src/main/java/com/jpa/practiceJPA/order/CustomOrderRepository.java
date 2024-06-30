package com.jpa.practiceJPA.order;

import java.util.List;

public interface CustomOrderRepository {
    public List<Order> search(OrderSearch orderSearch);
}
