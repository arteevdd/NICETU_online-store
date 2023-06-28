package test.project.onlineshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductListDto {

    private Integer productId;

    private String nameProduct;

    private Double price;

    private Integer count;

    private String description;

    private Double salePrice;

    private String nameProducer;

    private String road;

    public ProductListDto(Integer productId, String nameProduct, Double price, Integer count, String description, Double salePrice, String nameProducer, String road) {
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
        this.description = description;
        this.salePrice = salePrice;
        this.nameProducer = nameProducer;
        this.road = road;
    }
}
