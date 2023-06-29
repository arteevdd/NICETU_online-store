package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("CRUD - methods: User")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final User testUser;

    {
        testUser = User.builder()
                .firstName("Danil")
                .secondName("Arteev")
                .email("arteic4@yandex.ru")
                .roleId(new Role(1, "ROLE_USER"))
                .password("qwerty")
                .build();
    }

    @Test
    @DisplayName("When the correct user data will be returned")
    void findUserByEmail_ReturnsCorrectEntity() {
        Optional<User> user = userRepository.findUserByEmail(testUser.getEmail());
        assertNotEquals(Optional.empty(), user);
        user.ifPresent(
                value -> assertAll(
                        () -> assertEquals(testUser.getFirstName(), value.getFirstName()),
                        () -> assertEquals(testUser.getSecondName(), value.getSecondName()),
                        () -> assertEquals(testUser.getEmail(), value.getEmail()),
                        () -> assertEquals(value.getRoleId(), testUser.getRoleId()),
                        () -> assertTrue(passwordEncoder.matches(testUser.getPassword(), value.getPassword()))
                )
        );
    }
}
