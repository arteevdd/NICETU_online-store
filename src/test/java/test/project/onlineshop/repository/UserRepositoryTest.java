package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.project.onlineshop.entity.User;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@DisplayName("CRUD - methods: User")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final User testUser = new User("Антон", "Милехин", "anton88@mail.ru", "pass123");

    private static final Integer ID = 1;

    @Test
    @DisplayName("Select user by user_id")
    void findUserByUserId() {
        Optional<User> user = userRepository.findUserByUserId(ID);
        user.ifPresent(value -> assertEquals("arteic4@yandex.ru", value.getEmail()));
    }

    @Test
    @DisplayName("Select all users")
    void findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        userRepository.save(testUser);
        assertNotEquals(users.size(), ((List<User>) userRepository.findAll()).size());
    }

    @Test
    @DisplayName("Update user email by user_id")
    void updateEmail() {
        userRepository.updateEmail(ID, testUser.getEmail());
        Optional<User> user = userRepository.findUserByUserId(ID);
        user.ifPresent(value -> assertEquals(testUser.getEmail(), value.getEmail()));
    }

    @Test
    @DisplayName("Remove user by user_id")
    void deleteUserByUserId() {
        userRepository.save(testUser);
        assertNotEquals(Optional.empty(), userRepository.findUserByEmail(testUser.getEmail()));
        userRepository.deleteUserByUserId(userRepository.findUserByEmail(testUser.getEmail()).get().getUserId());
        Optional<User> user = userRepository.findUserByEmail(testUser.getEmail());
        assertEquals(Optional.empty(), user);
    }

    @Test
    @DisplayName("Save new user")
    void saveNewUser() {
        Optional<User> emptyUser = userRepository.findUserByEmail(testUser.getEmail());
        assertEquals(Optional.empty(), emptyUser);
        userRepository.save(testUser);
        Optional<User> newUser = userRepository.findUserByEmail(testUser.getEmail());
        newUser.ifPresent(value -> assertEquals(testUser.getEmail(), value.getEmail()));
    }
}