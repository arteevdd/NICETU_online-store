package test.project.onlineshop.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.Cart;
import test.project.onlineshop.exception.CartNotFoundException;
import test.project.onlineshop.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart findCartByCartId(Integer cartId) {
        Optional<Cart> cart = cartRepository.findCartByCartId(cartId);
        if (cart.isPresent()){
            return cart.get();
        }else {
            throw new CartNotFoundException("Cart not found!");
        }
    }

    @Override
    public List<Cart> findAll() {
        return (List<Cart>) cartRepository.findAll();
    }
}
