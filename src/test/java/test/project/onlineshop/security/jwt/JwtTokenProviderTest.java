package test.project.onlineshop.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import test.project.onlineshop.entity.Role;
import test.project.onlineshop.exception.InvalidJwtAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Security layer: Token Provider")
class JwtTokenProviderTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider.setSecretKey("random_secret_key");
        jwtTokenProvider.setValidityInMs(60000);
    }

    @Test
    @DisplayName("When the token was generated")
    void generateToken_WasSuccessful() {
        Role role = Role.builder().build();
        String email = "arteic4@yandex.ru";
        assertNotNull(jwtTokenProvider.generateToken(email, role));
    }

    @Test
    @DisplayName("When username is successfully received")
    void getUserName_WasSuccessful() {
        String email = "arteic4@yandex.ru";
        Role role = Role.builder().build();
        String userName = jwtTokenProvider.getUserName(jwtTokenProvider.generateToken(email, role));
        assertEquals(email, userName);
    }

    @Test
    @DisplayName("When the token has been successfully validated")
    void validateToken_WasSuccessful() {
        String email = "arteic4@yandex.ru";
        Role role = Role.builder().build();
        String token = jwtTokenProvider.generateToken(email, role);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("When the invalid token arrived")
    void validateToken_InvalidToken_ThrowsInvalidJwtAuthenticationException() {
        assertThrows(InvalidJwtAuthenticationException.class, () -> {
            jwtTokenProvider.validateToken("invalid_token");
        });
    }

    @Test
    @DisplayName("When the token was resolved")
    void resolveToken_Valid_ReturnsToken() {
        when(request.getHeader("Authorization")).thenReturn("Bearer test_token");
        assertEquals("test_token", jwtTokenProvider.resolveToken(request));
    }

    @Test
    @DisplayName("When the token is not allowed")
    void resolveToken_WasFailed_ReturnsNull() {
        when(request.getHeader("Authorization")).thenReturn("random_string");
        assertNull(jwtTokenProvider.resolveToken(request));
    }

    @Test
    @DisplayName("When authentication was successful")
    void getAuthentication() {
        String email = "arteic4@yandex.ru";
        Role role = Role.builder().roleId(1).roleName("ROLE_USER").build();
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role.getRoleName()));
        String token = jwtTokenProvider.generateToken(email, role);
        assertNotNull(token);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, "", authorities);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        assertNotNull(authentication);
        assertEquals(email, ((UserDetails) authentication.getPrincipal()).getUsername());
    }
}