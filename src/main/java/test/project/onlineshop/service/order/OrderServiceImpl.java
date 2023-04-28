package test.project.onlineshop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.Order;
import test.project.onlineshop.exception.OrderNotFoundException;
import test.project.onlineshop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order findOrderByOrderId(Integer orderId) {
        Optional<Order> order = orderRepository.findOrderByOrderId(orderId);
        if (order.isPresent()){
            return order.get();
        }else{
            throw new OrderNotFoundException("Order not found!");
        }
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }
}
