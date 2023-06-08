package test.project.onlineshop.service.order;

import test.project.onlineshop.dto.OrderDto;

import java.util.List;

public interface OrderService {

    void addNewOrders(List<OrderDto> orderDto);
}
