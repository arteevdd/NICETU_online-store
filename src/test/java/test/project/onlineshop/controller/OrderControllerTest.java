package test.project.onlineshop.controller;

import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.exception.RejectedTransactionException;
import test.project.onlineshop.service.order.OrderService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("Web layer: Order")
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    OrderDto order = OrderDto.builder()
            .productId(1)
            .quantity(2)
            .build();

    @Test
    @DisplayName("When add new order successfully created")
    void addNewOrders_ReturnsHttpStatusCreated() {
        doNothing().when(orderService).addNewOrders(Arrays.asList(order));

        var responseEntity = orderController.addNewOrders(Arrays.asList(order));

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("When cart can't created")
    void addNewOrders_ThrowsResponseStatusException_RejectedTransactionException_InternalServerError() {
        doThrow(new RejectedTransactionException("Buy transaction rejected!")).when(orderService).addNewOrders(Arrays.asList(order));

        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
                    orderController.addNewOrders(Arrays.asList(order));
                });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    @DisplayName("When product not found")
    void addNewOrders_ThrowsResponseStatusException_ProductNotFoundException_InternalServerError() {
        doThrow(new ProductNotFoundException("Product not found!")).when(orderService).addNewOrders(Arrays.asList(order));

        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
                    orderController.addNewOrders(Arrays.asList(order));
                });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    @DisplayName("When product have negative count")
    void addNewOrders_ThrowsResponseStatusException_BadRequest() {
        doThrow(new IllegalArgumentException("Negative product count")).when(orderService).addNewOrders(Arrays.asList(order));

        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
                    orderController.addNewOrders(Arrays.asList(order));
                });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}