package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.service.order.OrderService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewOrders(@RequestBody List<OrderDto> orderDtos){
        orderService.addNewOrders(orderDtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
