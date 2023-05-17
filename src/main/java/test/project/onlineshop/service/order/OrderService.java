package test.project.onlineshop.service.order;

import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.entity.Order;

import java.util.List;

public interface OrderService {

    Order findOrderByOrderId(Integer orderId);

    List<Order >addNewOrders(List<OrderDto> orderDto);

    List<Order> findAll();
}