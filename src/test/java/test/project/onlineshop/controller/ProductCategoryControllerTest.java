package test.project.onlineshop.controller;

import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.exception.ProductCategoryNotFoundException;
import test.project.onlineshop.service.product_category.ProductCategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Web layer: Product Category")
class ProductCategoryControllerTest {

    @Mock
    private ProductCategoryService productCategoryService;

    @InjectMocks
    private ProductCategoryController productCategoryController;

    @Test
    @DisplayName("When return correct products")
    void relationshipsBetweenCategoryAndProduct_ReturnsCorrectEntity() {
        int mainCategoryId = 1;

        List<ProductListDto> expected = new ArrayList<>();
        expected.add(ProductListDto.builder()
                .productId(6)
                .nameProduct("Смартфон Huawei P50")
                .price(39999.0)
                .count(25)
                .salePrice(35991.0)
                .description("Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории.")
                .road("HuaweiP50.jpg")
                .build()
        );
        expected.add(ProductListDto.builder()
                .productId(1)
                .nameProduct("Смартфон Apple iPhone 14")
                .price(86990.0)
                .count(15)
                .salePrice(78291.0)
                .description("iPhone 14 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone14.jpg")
                .build()
        );
        expected.add(ProductListDto.builder()
                .productId(2)
                .nameProduct("Смартфон Apple iPhone 13 Pro Max")
                .price(125990.0)
                .count(10)
                .salePrice(100792.0)
                .description("iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone13ProMax.jpg")
                .build()
        );
        expected.add(ProductListDto.builder()
                .productId(3)
                .nameProduct("Смартфон Apple iPhone 11")
                .price(53390.0)
                .count(20)
                .salePrice(48051.0)
                .description("iPhone 11 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone11.jpg")
                .build()
        );
        expected.add(ProductListDto.builder()
                .productId(4)
                .nameProduct("Смартфон Samsung Galaxy S8")
                .price(11950.0)
                .count(13)
                .salePrice(11950.0)
                .description("Samsung Galaxy S8 - еще один из лидеров на рынке телефонов.")
                .road("SamsungGalaxyS8.jpg")
                .build()
        );
        expected.add(ProductListDto.builder()
                .productId(5)
                .nameProduct("Наушники Apple AirPods Pro")
                .price(23999.0)
                .count(40)
                .salePrice(23999.0)
                .description("Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день.")
                .road("AirPodsPro.jpg")
                .build()
        );

        when(productCategoryService.findProductCategoriesByCategoryId(mainCategoryId)).thenReturn(expected);

        var response = productCategoryController.relationshipsBetweenCategoryAndProduct(mainCategoryId);

        assertNotNull(response);
        assertEquals(expected, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("When return correct products")
    void relationshipsBetweenCategoryAndProduct_ReturnsEmptyList_ThrowsProductCategoryNotFoundException() {
        when(productCategoryService.findProductCategoriesByCategoryId(anyInt())).thenThrow(ProductCategoryNotFoundException.class);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    productCategoryController.relationshipsBetweenCategoryAndProduct(anyInt());
                });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}