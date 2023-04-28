package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.entity.Cart;
import test.project.onlineshop.exception.CartNotFoundException;
import test.project.onlineshop.service.cart.CartService;

import java.util.List;

@RestController
@RequestMapping("/online-shop")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<Cart> findCartByCartId(@PathVariable("cartId") Integer cartId){
        try {
            return new ResponseEntity<>(cartService.findCartByCartId(cartId), HttpStatus.OK);
        }catch (CartNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> findAllCarts(){
        try {
            return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
        }catch (CartNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
