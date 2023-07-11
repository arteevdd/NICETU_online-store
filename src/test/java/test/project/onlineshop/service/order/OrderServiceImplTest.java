package test.project.onlineshop.service.order;

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
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.entity.*;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.repository.CartRepository;
import test.project.onlineshop.repository.OrderRepository;
import test.project.onlineshop.repository.ProductRepository;
import test.project.onlineshop.repository.UserRepository;
import test.project.onlineshop.service.jms.EmailSenderService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer: Order")
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setup() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("When the order was successfully created")
    void addNewOrders_WillBeCreated() {

        String testSubject = "Покупка успешно совершена!";
        String testMessage = "Вы приобрели: 1 продукта:\n" +
                "1) Смартфон Apple iPhone 14 количество: 2 цена: 78291.0\n" +
                "Общая стоимость заказа: 173980.0\n" +
                "Удачного пользования, ждём вас снова!";

        User existentUser = User.builder()
                .userId(1)
                .firstName("Danil")
                .secondName("Arteev")
                .email("arteic4@yandex.ru")
                .password("qwerty")
                .roleId(Role.builder().build())
                .build();

        Product testProduct = Product.builder()
                .productId(1)
                .nameProduct("Смартфон Apple iPhone 14")
                .price(86990.0)
                .count(15)
                .saleId(Sale.builder().build())
                .salePrice(78291.0)
                .description("iPhone 14 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone14.jpg")
                .build();

        Cart currentCart = Cart.builder().userId(existentUser).build();
        Cart returnedCart = Cart.builder().cartId(100).userId(existentUser).build();

        OrderDto order = OrderDto.builder()
                .productId(1)
                .quantity(2)
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(existentUser.getEmail());
        when(userRepository.findUserByEmail(existentUser.getEmail())).thenReturn(Optional.of(existentUser));
        when(cartRepository.save(currentCart)).thenReturn(returnedCart);
        when(productRepository.findProductByProductId(order.getProductId())).thenReturn(Optional.ofNullable(testProduct));
        when(orderRepository.save(new Order(testProduct, returnedCart, order.getQuantity(), testProduct.getSalePrice() * order.getQuantity()))).thenReturn(null);
        doNothing().when(productRepository).updateProductCountByProductId(order.getProductId(), testProduct.getCount() - order.getQuantity());
        doNothing().when(emailSenderService).sendEmail(existentUser.getEmail(), testSubject, testMessage);

        orderService.addNewOrders(Arrays.asList(order));

        assertEquals(existentUser.getEmail(), SecurityContextHolder.getContext().getAuthentication().getName());
        verify(securityContext, times(2)).getAuthentication();
        verify(authentication, times(2)).getName();
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(productRepository, times(1)).findProductByProductId(anyInt());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(productRepository, times(1)).updateProductCountByProductId(anyInt(), anyInt());
        verify(emailSenderService, times(1)).sendEmail(existentUser.getEmail(), testSubject, testMessage);
    }

    @Test
    @DisplayName("When product not existent")
    void addNewOrders_NegativeProductCount_TrowsIllegalArgumentException() {
        User existentUser = User.builder()
                .userId(1)
                .firstName("Danil")
                .secondName("Arteev")
                .email("arteic4@yandex.ru")
                .password("qwerty")
                .roleId(Role.builder().build())
                .build();

        Product testProduct = Product.builder()
                .productId(1)
                .nameProduct("Смартфон Apple iPhone 14")
                .price(86990.0)
                .count(0)
                .saleId(Sale.builder().build())
                .salePrice(78291.0)
                .description("iPhone 14 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone14.jpg")
                .build();

        OrderDto order = OrderDto.builder()
                .productId(1)
                .quantity(2)
                .build();

        Cart currentCart = Cart.builder().userId(existentUser).build();
        Cart returnedCart = Cart.builder().cartId(100).userId(existentUser).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(existentUser.getEmail());
        when(userRepository.findUserByEmail(existentUser.getEmail())).thenReturn(Optional.of(existentUser));
        when(cartRepository.save(currentCart)).thenReturn(returnedCart);
        when(productRepository.findProductByProductId(anyInt())).thenReturn(Optional.ofNullable(testProduct));

        assertThrows(IllegalArgumentException.class, () -> orderService.addNewOrders(Arrays.asList(order)));
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getName();
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(productRepository, times(1)).findProductByProductId(anyInt());
    }

    @Test
    @DisplayName("When the product ended")
    void addNewOrders_ProductNotExistent_TrowsProductNotFoundException() {
        User existentUser = User.builder()
                .userId(1)
                .firstName("Danil")
                .secondName("Arteev")
                .email("arteic4@yandex.ru")
                .password("qwerty")
                .roleId(Role.builder().build())
                .build();

        OrderDto order = OrderDto.builder()
                .productId(1)
                .quantity(2)
                .build();

        Cart currentCart = Cart.builder().userId(existentUser).build();
        Cart returnedCart = Cart.builder().cartId(100).userId(existentUser).build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(existentUser.getEmail());
        when(userRepository.findUserByEmail(existentUser.getEmail())).thenReturn(Optional.of(existentUser));
        when(cartRepository.save(currentCart)).thenReturn(returnedCart);
        when(productRepository.findProductByProductId(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> orderService.addNewOrders(Arrays.asList(order)));
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getName();
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(productRepository, times(1)).findProductByProductId(anyInt());
    }
}