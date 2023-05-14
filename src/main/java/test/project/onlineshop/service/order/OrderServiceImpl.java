package test.project.onlineshop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.OrderDto;
import test.project.onlineshop.entity.Cart;
import test.project.onlineshop.entity.Order;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.exception.OrderNotFoundException;
import test.project.onlineshop.repository.CartRepository;
import test.project.onlineshop.repository.OrderRepository;
import test.project.onlineshop.repository.ProductRepository;
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

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository, EmailSenderService emailSenderService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public Order findOrderByOrderId(Integer orderId) {
        Optional<Order> order = orderRepository.findOrderByOrderId(orderId);
        if (order.isPresent()){
            return order.get();
        }else{
            throw new OrderNotFoundException("Order not found!");
        }
    }

    @Override
    public List<Order> addNewOrders(List<OrderDto> orderDto) {
//        TODO: переписать на автоматическое определение
        String emailBuyer = "arteev.dd@gmail.com";
        StringBuilder stringBuilder = new StringBuilder();
        Optional<Cart> cart = cartRepository.findCartByCartId(4);
        List<Product> products = new ArrayList<>();
        int count = 1;
        double totalPrice = 0;
        if (cart.isPresent()){
            for (OrderDto order : orderDto) {
                Product currentProduct = productRepository.findProductByProductId(order.getProductId()).get();
                stringBuilder.append(count++ + ") " + currentProduct.getNameProduct() + " количество: " + order.getQuantity() +" цена: " + currentProduct.getPrice() + "\n");
                totalPrice += currentProduct.getPrice();
                products.add(productRepository.findProductByProductId(order.getProductId()).get());
                Integer quantity = order.getQuantity();
                orderRepository.save(
                        new Order(
                                currentProduct,
                                cart.get(),
                                quantity,
                                productRepository.findProductByProductId(order.getProductId()).get().getPrice() * quantity
                        )
                );
                productRepository.updateProductCountByProductId(order.getProductId(), productRepository.findProductByProductId(order.getProductId()).get().getCount() - order.getQuantity());
            }
        }
        emailSenderService.sendEmail(
                emailBuyer,
                "Покупка успешно совершена!",
                "Вы приобрели: " + products.size() + " продукта:\n" + stringBuilder + "Общая стоимость заказа: " + totalPrice + "\nУдачного пользования, ждём вас снова!\n\n" + "ООО <<Гребцы на галлерах>>.");
        return (List<Order>) orderRepository.findOrdersByCartId(4);
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }
}
