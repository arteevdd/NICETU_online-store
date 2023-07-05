package test.project.onlineshop.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.entity.Sale;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer: Product")
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private final List<Product> expectedProducts = new ArrayList<>();

    Sale sale = Sale.builder().build();

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
    @DisplayName("When returns the correct product for each product_id")
    void findProductByProductId_ForEachProductId_ReturnCorrectEntity() {
        for (Product p: expectedProducts) {
            when(productRepository.findProductByProductId(p.getProductId())).thenReturn(Optional.ofNullable(expectedProducts.get(p.getProductId() - 1)));
            Product actual = productService.findProductByProductId(p.getProductId());
            assertEquals(expectedProducts.get(p.getProductId() - 1), actual);
        }
        verify(productRepository, times(6)).findProductByProductId(anyInt());
    }

    @Test
    @DisplayName("When looking for a non-existent product")
    void findProductByProductId_NotExistentProductId_ThrowsProductNotFoundException() {
        when(productRepository.findProductByProductId(anyInt())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.findProductByProductId(anyInt()));
        verify(productRepository, times(1)).findProductByProductId(anyInt());
    }

    @Test
    @DisplayName("When returns the correct products")
    void findAll_ReturnCorrectEntities() {
        when(productRepository.findAll()).thenReturn(expectedProducts);
        assertEquals(expectedProducts, productService.findAll());
        verify(productRepository, times(1)).findAll();
    }
}