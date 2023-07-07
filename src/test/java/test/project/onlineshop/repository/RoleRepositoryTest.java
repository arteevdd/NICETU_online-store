package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.project.onlineshop.entity.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("Repository layer: Role")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private final Role expectedRole;
    private final Role notExistRole;

    {
        expectedRole = Role.builder()
                .roleId(1)
                .roleName("ROLE_USER")
                .build();

        notExistRole = Role.builder()
                .roleId(2)
                .roleName("ROLE_ADMIN")
                .build();
    }

    @Test
    @DisplayName("When return correct Role Entity")
    void findRoleByRoleName_ReturnsCorrectEntity() {
        Optional<Role> role = roleRepository.findRoleByRoleName(expectedRole.getRoleName());
        assertNotEquals(Optional.empty(), role);
        role.ifPresent(value -> assertEquals(expectedRole, value));
    }

    @Test
    @DisplayName("When will be returned Optional.empty()")
    void findRoleByRoleName_ReturnsEmptyOptional() {
        Optional<Role> role = roleRepository.findRoleByRoleName(notExistRole.getRoleName());
        assertEquals(Optional.empty(), role);
    }
}