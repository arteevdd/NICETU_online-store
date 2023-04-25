package test.project.onlineshop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String nameProduct;

    private Double price;

    private Integer count;

    private Integer producerId;
}
