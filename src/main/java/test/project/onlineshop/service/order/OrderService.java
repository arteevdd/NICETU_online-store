package test.project.onlineshop.service.order;

import test.project.onlineshop.entity.Order;

import java.util.List;

public interface OrderService {

    Order findOrderByOrderId(Integer orderId);

    List<Order> findAll();
}
