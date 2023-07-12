package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.project.onlineshop.entity.Cart;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.entity.User;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("Repository layer: Cart")
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    private final User testUserEntity;

    {
        testUserEntity =
                User.builder()
                        .firstName("Danil")
                        .secondName("Arteev")
                        .email("arteic4@yandex.ru")
                        .roleId(Role.builder()
                                .roleId(1)
                                .roleName("ROLE_USER")
                                .build())
                        .password("qwerty")
                        .build();
    }

    @Test
    @DisplayName("Will saved new cart for existent user")
    void saveNewCartEntity_ForExistentUser() {
        Optional<User> existentUser = userRepository.findUserByEmail(testUserEntity.getEmail());
        assertNotEquals(Optional.empty(), existentUser);
        Iterable<Cart> allCartsBeforeSaveNewCart = cartRepository.findAll();
        existentUser.ifPresent(value -> cartRepository.save(Cart.builder()
                .userId(existentUser.get()).transactionTime(new Timestamp(2023, 1, 2, 14, 12, 0, 0))
                .build())
        );
        Iterable<Cart> allCartsAfterSaveNewCart = cartRepository.findAll();
        assertNotEquals(allCartsBeforeSaveNewCart, allCartsAfterSaveNewCart);
    }
}