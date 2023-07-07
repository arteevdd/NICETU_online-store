package test.project.onlineshop.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Security layer: JWT Filter")
class JwtFilterTest {

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setup() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("When the token is valid and not null")
    void doFilter_WasSuccessful() throws ServletException, IOException {
        String token = "valid_token";
        when(tokenProvider.resolveToken(request)).thenReturn(token);
        when(tokenProvider.validateToken(token)).thenReturn(true);
        when(tokenProvider.getAuthentication(token)).thenReturn(authentication);

        jwtFilter.doFilter(request, response, filterChain);

        verify(securityContext, times(1)).setAuthentication(authentication);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    @DisplayName("When the token is null")
    void doFilter_whenTokenIsNull_NotSetAuthentication() throws ServletException, IOException {
        when(tokenProvider.resolveToken(request)).thenReturn(null);

        jwtFilter.doFilter(request, response, filterChain);

        verify(securityContext, never()).setAuthentication(authentication);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("When did the invalid token arrive")
    void doFilter_whenTokenIsNotValid_NotSetAuthentication() throws ServletException, IOException {
        String token = "invalid_token";
        when(tokenProvider.resolveToken(request)).thenReturn(token);
        when(tokenProvider.validateToken(token)).thenReturn(false);

        jwtFilter.doFilter(request, response, filterChain);

        verify(securityContext, never()).setAuthentication(authentication);
        verify(filterChain).doFilter(request, response);
    }
}