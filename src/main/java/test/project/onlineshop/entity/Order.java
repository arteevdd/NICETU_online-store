package test.project.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product productId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cartId")
    private Cart cartId;

    public Order() {
    }

    public Order(Product productId, Cart cartId,Integer quantity, Double price) {
        this.productId = productId;
        this.cartId = cartId;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", productId=" + productId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(quantity, order.quantity) && Objects.equals(price, order.price) && Objects.equals(productId, order.productId) && Objects.equals(cartId, order.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, quantity, price, productId, cartId);
    }
}
