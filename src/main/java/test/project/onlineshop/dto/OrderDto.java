package test.project.onlineshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class OrderDto {

    private Integer productId;

    private Integer quantity;

    public OrderDto(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
