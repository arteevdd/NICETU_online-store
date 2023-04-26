package test.project.onlineshop.service.cart;

import test.project.onlineshop.entity.Cart;

import java.util.List;

public interface CartService {

    Cart findCartByCartId(Integer cartId);

    List<Cart> findAll();
}
