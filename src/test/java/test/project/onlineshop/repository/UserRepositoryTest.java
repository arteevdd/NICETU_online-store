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
@DisplayName("Repository layer: User")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final User expectedUser;

    private final User nonExistentTestUser;

    {
        passwordEncoder = new BCryptPasswordEncoder();

        expectedUser =
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

        nonExistentTestUser =
                User.builder()
                .firstName("Viktor")
                .secondName("Pavlov")
                .email("viktorpavlov@bk.ru")
                .roleId(Role.builder()
                        .roleId(1)
                        .roleName("ROLE_USER")
                        .build())
                .password("pwd")
                .build();
    }

    @Test
    @DisplayName("When the correct user data will be returned")
    void findUserByEmail_ReturnsCorrectEntity() {
        Optional<User> user = userRepository.findUserByEmail(expectedUser.getEmail());
        assertNotEquals(Optional.empty(), user);
        user.ifPresent(
                value -> assertAll(
                        () -> assertEquals(expectedUser.getFirstName(), value.getFirstName()),
                        () -> assertEquals(expectedUser.getSecondName(), value.getSecondName()),
                        () -> assertEquals(expectedUser.getEmail(), value.getEmail()),
                        () -> assertEquals(expectedUser.getRoleId(), value.getRoleId()),
                        () -> assertTrue(passwordEncoder.matches(expectedUser.getPassword(), value.getPassword()))
                )
        );
    }

    @Test
    @DisplayName("When looking for a non-existent user")
    void findUserByEmail_ReturnsEmptyOptional() {
        Optional<User> actual = userRepository.findUserByEmail(nonExistentTestUser.getEmail());
        assertEquals(Optional.empty(), actual);
    }

    @Test
    @DisplayName("Save and return a not exist user by email")
    void saveNotExistUser_AndFindByEmail_ReturnsCorrectEntity() {
        Optional<User> actual = userRepository.findUserByEmail(nonExistentTestUser.getEmail());
        assertEquals(Optional.empty(), actual);
        userRepository.save(nonExistentTestUser);
        actual = userRepository.findUserByEmail(nonExistentTestUser.getEmail());
        actual.ifPresent(value -> assertEquals(nonExistentTestUser, value));
    }
}
