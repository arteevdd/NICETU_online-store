package test.project.onlineshop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.entity.Cart;
import test.project.onlineshop.entity.Order;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.ProductNotFoundException;
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
        for (OrderDto order : orderDto) {
            Optional<Product> currentProduct = productRepository.findProductByProductId(order.getProductId());
            if (currentProduct.isPresent()) {
                if (currentProduct.get().getCount() > 0) {
                    stringBuilder.append(count++ + ") " + currentProduct.get().getNameProduct() + "\n    Количество: " + order.getQuantity() + "\n    Цена: " + currentProduct.get().getSalePrice() + "\n");
                    totalPrice += currentProduct.get().getPrice() * order.getQuantity();
                    products.add(currentProduct.get());
                    orderRepository.save(
                            new Order(
                                    currentProduct.get(),
                                    currentCart,
                                    order.getQuantity(),
                                    currentProduct.get().getSalePrice() * order.getQuantity()
                            )
                    );
                    productRepository.updateProductCountByProductId(order.getProductId(), currentProduct.get().getCount() - order.getQuantity());
                } else {
                    throw new IllegalArgumentException("Negative product count");
                }
            } else {
                throw new ProductNotFoundException("Product not found!");
            }
        }
        String product = "";
        switch(products.size()){
            case 1:
                product = " товар";
                break;
            case 2:
            case 4:
            case 3:
                product = " товара";
                break;
            default: product = " товаров";
        }
        emailSenderService.sendEmail(
                emailBuyer,
                "Покупка успешно совершена!",
                "Вы приобрели " + products.size() + product + ":\n" + stringBuilder + "Общая стоимость заказа: " + totalPrice + "\nУдачного пользования, ждём вас снова!");
    }
}
