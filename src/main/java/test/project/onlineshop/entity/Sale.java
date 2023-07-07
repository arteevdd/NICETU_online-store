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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(saleId, sale.saleId) && Objects.equals(salePercent, sale.salePercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, salePercent);
    }
}
