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
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.entity.Sale;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.service.product.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Web layer: Product")
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private final List<Product> expectedProducts = new ArrayList<>();

    private Sale sale = Sale.builder().build();

    {
        expectedProducts.add(Product.builder()
                .productId(1)
                .nameProduct("Смартфон Apple iPhone 14")
                .price(86990.0)
                .count(15)
                .saleId(sale)
                .salePrice(78291.0)
                .description("iPhone 14 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone14.jpg")
                .build()
        );
        expectedProducts.add(Product.builder()
                .productId(2)
                .nameProduct("Смартфон Apple iPhone 13 Pro Max")
                .price(125990.0)
                .count(10)
                .saleId(sale)
                .salePrice(100792.0)
                .description("iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone13ProMax.jpg")
                .build()
        );
        expectedProducts.add(Product.builder()
                .productId(3)
                .nameProduct("Смартфон Apple iPhone 11")
                .price(53390.0)
                .count(20)
                .saleId(sale)
                .salePrice(48051.0)
                .description("iPhone 11 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone11.jpg")
                .build()
        );
        expectedProducts.add(Product.builder()
                .productId(4)
                .nameProduct("Смартфон Samsung Galaxy S8")
                .price(11950.0)
                .count(13)
                .saleId(sale)
                .salePrice(11950.0)
                .description("Samsung Galaxy S8 - еще один из лидеров на рынке телефонов.")
                .road("SamsungGalaxyS8.jpg")
                .build()
        );
        expectedProducts.add(Product.builder()
                .productId(5)
                .nameProduct("Наушники Apple AirPods Pro")
                .price(23999.0)
                .count(40)
                .saleId(sale)
                .salePrice(23999.0)
                .description("Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день.")
                .road("AirPodsPro.jpg")
                .build()
        );
        expectedProducts.add(Product.builder()
                .productId(6)
                .nameProduct("Смартфон Huawei P50")
                .price(39999.0)
                .count(25)
                .saleId(sale)
                .salePrice(35991.0)
                .description("Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории.")
                .road("HuaweiP50.jpg")
                .build()
        );
    }

    @Test
    @DisplayName("When return correct product entity with product_id = 1")
    void findProductListDtoByProductId_ReturnsCorrectEntity() {
        int testProductId = 1;

        when(productService.findProductByProductId(testProductId)).thenReturn(expectedProducts.get(0));

        var response = productController.findProductListDtoByProductId(testProductId);

        assertNotNull(response);
        assertEquals(expectedProducts.get(0), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("When product not found")
    void findProductListDtoByProductId_ReturnsResponseStatusException_NotFound() {
        when(productService.findProductByProductId(anyInt())).thenThrow(ProductNotFoundException.class);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    productController.findProductListDtoByProductId(anyInt());
                });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("When returns correct products")
    void findAllProducts_ReturnsCorrectProductsEntity() {
        when(productService.findAll()).thenReturn(expectedProducts);

        var response = productController.findAllProducts();

        assertNotNull(response);
        assertEquals(expectedProducts, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("When no one product not returns")
    void findAllProducts_ReturnsEmptyList_ThrowsResponseStatusException_NotFound() {
        when(productService.findAll()).thenThrow(ProductNotFoundException.class);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    productController.findAllProducts();
                });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}