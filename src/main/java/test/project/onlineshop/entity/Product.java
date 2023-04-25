package test.project.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name_product", length = 255, unique = true, nullable = false)
    private String nameProduct;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "count")
    private Integer count;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id")
    private Producer producerId;

    @OneToMany(mappedBy = "productId")
    private Collection<Order> orders;

    @OneToMany(mappedBy = "productId")
    private Collection<ProductCategory> productCategories;

    public Product() {
    }

    public Product(String nameProduct, Double price, Integer count) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", nameProduct='" + nameProduct + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", producerId=" + producerId +
                '}';
    }
}
