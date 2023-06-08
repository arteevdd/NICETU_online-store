package test.project.onlineshop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.entity.Cart;
import test.project.onlineshop.entity.Order;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.RejectedTransactionException;
import test.project.onlineshop.repository.CartRepository;
import test.project.onlineshop.repository.OrderRepository;
import test.project.onlineshop.repository.ProductRepository;
import test.project.onlineshop.repository.UserRepository;
import test.project.onlineshop.service.jms.EmailSenderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final EmailSenderService emailSenderService;

    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository,
                            ProductRepository productRepository, EmailSenderService emailSenderService,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
    }

    @Override
    public void addNewOrders(List<OrderDto> orderDto) {
        String emailBuyer = SecurityContextHolder.getContext().getAuthentication().getName();
        StringBuilder stringBuilder = new StringBuilder();
        Optional<User> currentUser = userRepository.findUserByEmail(emailBuyer);
        Cart currentCart = cartRepository.save(new Cart(currentUser.get()));
        List<Product> products = new ArrayList<>();
        int count = 1;
        double totalPrice = 0;
        if (currentCart != null){
            for (OrderDto order : orderDto) {
                Product currentProduct = productRepository.findProductByProductId(order.getProductId()).get();
                stringBuilder.append(count++ + ") " + currentProduct.getNameProduct() + " количество: " + order.getQuantity() +" цена: " + currentProduct.getPrice() + "\n");
                totalPrice += currentProduct.getPrice() * order.getQuantity();
                products.add(productRepository.findProductByProductId(order.getProductId()).get());
                Integer quantity = order.getQuantity();
                orderRepository.save(
                        new Order(
                                currentProduct,
                                currentCart,
                                quantity,
                                productRepository.findProductByProductId(order.getProductId()).get().getPrice() * quantity
                        )
                );
                productRepository.updateProductCountByProductId(order.getProductId(), productRepository.findProductByProductId(order.getProductId()).get().getCount() - order.getQuantity());
            }
            emailSenderService.sendEmail(
                    emailBuyer,
                    "Покупка успешно совершена!",
                    "Вы приобрели: " + products.size() + " продукта:\n" + stringBuilder + "Общая стоимость заказа: " + totalPrice + "\nУдачного пользования, ждём вас снова!");
        }else {
            throw new RejectedTransactionException("Buy transaction rejected!");
        }
    }
}
