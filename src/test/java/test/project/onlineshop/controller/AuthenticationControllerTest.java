package test.project.onlineshop.controller;

import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.UserRequest;
import test.project.onlineshop.exception.RoleNotFoundException;
import test.project.onlineshop.exception.UserExistentException;
import test.project.onlineshop.exception.UserNotFoundException;
import test.project.onlineshop.service.user.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Web layer: Authentication")
class AuthenticationControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    UserRequest userRequest = UserRequest.builder()
            .firstName("Danil")
            .secondName("Arteev")
            .email("arteic4@yandex.ru")
            .password("qwerty")
            .build();

    AuthRequest authRequest = AuthRequest.builder()
            .email("arteic4@yandex.ru")
            .password("qwerty")
            .build();

    @Test
    @DisplayName("When the registration was successful")
    void registration_WasSuccessful_ReturnsResponseEntityCreated() {
        doNothing().when(userService).registration(userRequest);

        ResponseEntity response = authenticationController.registration(userRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("When an invalid email pattern is passed")
    void registration_InvalidEmailPattern_ThrowsResponseStatusException_IllegalArgumentException_BadRequest() {
        doThrow(IllegalArgumentException.class).when(userService).registration(userRequest);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    authenticationController.registration(userRequest);
                });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("When the user already exists")
    void registration_UserExistent_ThrowsResponseStatusException_UserExistentException_Conflict() {
        doThrow(UserExistentException.class).when(userService).registration(userRequest);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    authenticationController.registration(userRequest);
                });
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    @DisplayName("When the role is not found")
    void registration_RoleNotFound_ThrowsResponseStatusException_RoleNotFoundException_InternalServerError() {
        doThrow(RoleNotFoundException.class).when(userService).registration(userRequest);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    authenticationController.registration(userRequest);
                });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    @DisplayName("When login was successful")
    void login_WasSuccessful() {
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("firstName", userRequest.getFirstName());
        expectedResponse.put("secondName", userRequest.getSecondName());
        expectedResponse.put("email", userRequest.getEmail());
        expectedResponse.put("token", "token");
        when(userService.login(authRequest)).thenReturn(expectedResponse);

        var actualResponse = authenticationController.login(authRequest);

        assertEquals(expectedResponse, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    @DisplayName("When user not existent")
    void login_UserNotFound_ThrowsResponseStatusException_UserNotFoundException_Conflict() {
        doThrow(UserNotFoundException.class).when(userService).login(authRequest);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    authenticationController.login(authRequest);
                });
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    @DisplayName("When an invalid password is passed")
    void login_InvalidPassword_ThrowsResponseStatusException_BadCredentialsException_Unauthorized() {
        doThrow(BadCredentialsException.class).when(userService).login(authRequest);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    authenticationController.login(authRequest);
                });
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
}