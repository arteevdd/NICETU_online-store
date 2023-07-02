package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.project.onlineshop.entity.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("CRUD - methods: Order")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("Save new order for existent cart")
    void saveNewOrder_ForExistentCart() {
        List<Cart> carts = (List<Cart>) cartRepository.findAll();
        Optional<Product> product = productRepository.findProductByProductId(1);
        assertNotEquals(Optional.empty(), product);
        Iterable<Order> ordersBeforeSaveNewOrder = orderRepository.findAll();
        product.ifPresent(value -> orderRepository.save(Order.builder()
                .cartId(carts.get(0))
                .productId(value).quantity(2)
                .price(value.getPrice() * 2)
                .build()
        ));
        Iterable<Order> ordersAfterSaveNewOrder = orderRepository.findAll();
        assertNotEquals(ordersBeforeSaveNewOrder, ordersAfterSaveNewOrder);
    }
}