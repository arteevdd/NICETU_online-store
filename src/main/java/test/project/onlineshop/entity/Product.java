package test.project.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
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

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "road")
    private String road;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id")
    private Sale saleId;

    @JsonIgnore
    @OneToMany(mappedBy = "productId")
    private Collection<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "productId")
    private Collection<ProductCategory> productCategories;

    public Product() {
    }

    public Product(String nameProduct, Double price, Integer count, String description, Double salePrice, Sale saleId) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
        this.description = description;
        this.salePrice = salePrice;
        this.saleId = saleId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", nameProduct='" + nameProduct + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", description='" + description + '\'' +
                ", salePrice=" + salePrice +
                ", road='" + road + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(nameProduct, product.nameProduct)
                && Objects.equals(price, product.price) && Objects.equals(count, product.count)
                && Objects.equals(description, product.description) && Objects.equals(salePrice, product.salePrice)
                && Objects.equals(road, product.road) && Objects.equals(saleId, product.saleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, nameProduct, price, count, description, salePrice, road, saleId);
    }
}
