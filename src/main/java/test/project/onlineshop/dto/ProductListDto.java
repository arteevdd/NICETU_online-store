package test.project.onlineshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductListDto {

    private String nameProduct;

    private Double price;

    private Integer count;

    private String nameProducer;

    public ProductListDto(String nameProduct, Double price, Integer count, String nameProducer) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
        this.nameProducer = nameProducer;
    }
}
