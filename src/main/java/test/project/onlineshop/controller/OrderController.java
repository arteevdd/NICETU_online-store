package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.entity.Order;
import test.project.onlineshop.exception.OrderNotFoundException;
import test.project.onlineshop.service.order.OrderService;

import java.util.List;

@RestController
@RequestMapping("/online-shop")
public class OrderController {

    private final OrderService orderService;

    @Autowired

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> findOrderByOrderId(@PathVariable("orderId") Integer orderId){
        try {
            return new ResponseEntity<>(orderService.findOrderByOrderId(orderId), HttpStatus.OK);
        }catch (OrderNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> findAllOrders(){
        try {
            return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
        }catch (OrderNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> addNewOrders(@RequestBody List<OrderDto> orderDtos){
        return new ResponseEntity<>(orderService.addNewOrders(orderDtos), HttpStatus.OK);
    }
}
