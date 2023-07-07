package test.project.onlineshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
public class ProductListDto {

    private Integer productId;

    private String nameProduct;

    private Double price;

    private Integer count;

    private String description;

    private Double salePrice;

    private String road;

    public ProductListDto(Integer productId, String nameProduct, Double price, Integer count, String description, Double salePrice, String road) {
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
        this.description = description;
        this.salePrice = salePrice;
        this.road = road;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductListDto that = (ProductListDto) o;
        return Objects.equals(productId, that.productId) && Objects.equals(nameProduct, that.nameProduct) && Objects.equals(price, that.price) && Objects.equals(count, that.count) && Objects.equals(description, that.description) && Objects.equals(salePrice, that.salePrice) && Objects.equals(road, that.road);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, nameProduct, price, count, description, salePrice, road);
    }
}
