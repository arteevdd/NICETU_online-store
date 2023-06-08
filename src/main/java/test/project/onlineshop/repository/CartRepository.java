package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
}
