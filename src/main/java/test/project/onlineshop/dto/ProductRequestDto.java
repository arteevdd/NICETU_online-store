package test.project.onlineshop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequestDto {

    private String nameProduct;

    private Double price;

    private Double salePrice;

    private Integer count;

    private Integer producerId;

    private String description;

    private Integer saleId;
}
