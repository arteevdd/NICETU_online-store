package test.project.onlineshop.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.RegistrationRequest;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.RoleNotFoundException;
import test.project.onlineshop.exception.UserExistentException;
import test.project.onlineshop.exception.UserNotFoundException;
import test.project.onlineshop.repository.RoleRepository;
import test.project.onlineshop.repository.UserRepository;
import test.project.onlineshop.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer: User")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("When the registration was successful")
    void registration_WasSuccessful() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .firstName("ValidFirstName")
                .secondName("ValidSecondName")
                .email("valid@email.com")
                .password("pwd")
                .build();

        Role expectedRole = Role.builder()
                .roleId(1)
                .roleName("ROLE_USER")
                .build();

        User user = new User(
                registrationRequest.getFirstName(),
                registrationRequest.getSecondName(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                expectedRole
        );

        when(userRepository.findUserByEmail(registrationRequest.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findRoleByRoleName(anyString())).thenReturn(Optional.ofNullable(expectedRole));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.registration(registrationRequest);

        verify(userRepository, times(1)).findUserByEmail(registrationRequest.getEmail());
        verify(roleRepository, times(1)).findRoleByRoleName(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("When an invalid email pattern was passed during registration")
    void registration_EmailInvalid_ThrowsIllegalArgumentException() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("invalid@email.r")
                .build();
        assertThrows(IllegalArgumentException.class, () -> userService.registration(registrationRequest));
    }

    @Test
    @DisplayName("When such a user is already registered")
    void registration_UserExistent_ThrowsUserExistentException() {
        User expectedUser = User.builder()
                .firstName("Danil")
                .secondName("Arteev")
                .email("arteic4@yandex.ru")
                .roleId(Role.builder()
                        .roleId(1)
                        .roleName("ROLE_USER")
                        .build())
                .password(passwordEncoder.encode("qwerty"))
                .build();

        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("arteic4@yandex.ru")
                .build();

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(expectedUser));

        assertThrows(UserExistentException.class, () -> userService.registration(registrationRequest));
        verify(userRepository, times(1)).findUserByEmail(anyString());
    }

    @Test
    @DisplayName("When ROLE_USER not existent")
    void registration_RoleUserNotExistent_ThrowsRoleNotFoundException() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("notExistent@email.com")
                .build();

        when(roleRepository.findRoleByRoleName(anyString())).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userService.registration(registrationRequest));
        verify(roleRepository, times(1)).findRoleByRoleName(anyString());
    }

    @Test
    @DisplayName("When there is no such user")
    void login_UserNotExistent_ThrowsUserNotFoundException() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("notExistent@email.com")
                .build();

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login(authRequest));
        verify(userRepository, times(1)).findUserByEmail(anyString());
    }

    @Test
    @DisplayName("When an invalid password is passed")
    void login_InvalidPassword_ThrowsBadCredentialsException() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("arteic4@yandex.ru")
                .password("pwd")
                .build();

        User expectedUser = User.builder()
                .password("dwp")
                .build();

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(expectedUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> userService.login(authRequest));
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }


    @Test
    @DisplayName("When the login was successful")
    public void login_WasSuccess() {
        User expected = User.builder()
                .firstName("Danil")
                .secondName("Arteev")
                .email("arteic4@yandex.ru")
                .password(passwordEncoder.encode("qwerty"))
                .roleId(Role.builder()
                        .roleId(1)
                        .roleName("ROLE_USER")
                        .build())
                .build();

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("firstName", expected.getFirstName());
        expectedResponse.put("secondName", expected.getSecondName());
        expectedResponse.put("token", "token");


        AuthRequest authRequest = AuthRequest.builder()
                .email(expected.getEmail())
                .password("qwerty")
                .build();

        when(userRepository.findUserByEmail(authRequest.getEmail())).thenReturn(Optional.of(expected));
        when(passwordEncoder.matches(authRequest.getPassword(), expected.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(authRequest.getEmail(), expected.getRoleId())).thenReturn(expectedResponse.get("token"));

        Map<String, String> response = userService.login(authRequest);

        assertNotNull(response);
        assertEquals(expectedResponse.get("firstName"), response.get("firstName"));
        assertEquals(expectedResponse.get("secondName"), response.get("secondName"));
        assertEquals(expectedResponse.get("token"), response.get("token"));
        verify(userRepository, times(1)).findUserByEmail(authRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(authRequest.getPassword(), expected.getPassword());
        verify(jwtTokenProvider, times(1)).generateToken(authRequest.getEmail(), expected.getRoleId());
    }
}