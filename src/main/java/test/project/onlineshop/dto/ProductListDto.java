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

    private Integer productId;

    private String nameProduct;

    private Double price;

    private Integer count;

    private String description;

    private Double salePrice;

    private String nameProducer;

    public ProductListDto(Integer productId, String nameProduct, Double price, Integer count, String description, Double salePrice, String nameProducer) {
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
        this.description = description;
        this.salePrice = salePrice;
        this.nameProducer = nameProducer;
    }
}
