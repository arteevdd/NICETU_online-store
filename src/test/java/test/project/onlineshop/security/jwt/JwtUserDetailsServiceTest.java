package test.project.onlineshop.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.UserNotFoundException;
import test.project.onlineshop.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Security layer: JWT User Details")
class JwtUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtUserDetailsService userDetailsService;

    @Test
    @DisplayName("When the user is successfully loaded")
    void loadUserByUsername_WasSuccessful() {
        User expectedUser = User.builder()
                .userId(1)
                .firstName("Danil")
                .secondName("Arteev")
                .roleId(Role.builder()
                        .roleId(1)
                        .roleName("ROLE_USER")
                        .build())
                .email("arteic4@yandex.ru")
                .password("qwerty")
                .build();
        when(userRepository.findUserByEmail("arteic4@yandex.ru")).thenReturn(Optional.ofNullable(expectedUser));

        UserDetails actualUser = userDetailsService.loadUserByUsername(expectedUser.getEmail());

        verify(userRepository, times(1)).findUserByEmail(anyString());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("When the user is not loaded")
    void loadUserByUserName_WasFailure() {
        when(userRepository.findUserByEmail(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userDetailsService.loadUserByUsername(anyString()));
    }
}