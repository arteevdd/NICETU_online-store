package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    Optional<Order> findOrderByOrderId(Integer orderId);

    Iterable<Order> findAll();

    Iterable<Order> findOrdersByCartId(Integer cartId);

}
