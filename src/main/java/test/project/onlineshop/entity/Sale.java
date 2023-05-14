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
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id", nullable = true)
    private Integer saleId;

    @Column(name = "sale_percent")
    private Integer salePercent;

    @JsonIgnore
    @OneToMany(mappedBy = "saleId")
    private Collection<Product> products;

    public Sale() {
    }

    public Sale(Integer salePercent) {
        this.salePercent = salePercent;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", salePercent=" + salePercent +
                '}';
    }
}
