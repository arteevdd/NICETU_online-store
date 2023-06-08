package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
