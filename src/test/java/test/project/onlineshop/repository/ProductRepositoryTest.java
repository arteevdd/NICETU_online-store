package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.entity.Sale;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("CRUD - methods: Product")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private final List<Sale> correctTestSale = new ArrayList<>();

    private final List<Product> correctTestProducts = new ArrayList<>();

    {
        correctTestSale.add(Sale.builder().
                saleId(1).
                salePercent(0)
                .build()
        );
        correctTestSale.add(Sale.builder().
                saleId(2).
                salePercent(10)
                .build()
        );
        correctTestSale.add(Sale.builder().
                saleId(3).
                salePercent(20)
                .build()
        );

        correctTestProducts.add(Product.builder()
                .productId(1)
                .nameProduct("Смартфон Apple iPhone 14")
                .price(86990.0)
                .count(15)
                .saleId(correctTestSale.get(1))
                .salePrice(78291.0)
                .description("iPhone 14 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone14.jpg")
                .build()
        );
        correctTestProducts.add(Product.builder()
                .productId(2)
                .nameProduct("Смартфон Apple iPhone 13 Pro Max")
                .price(125990.0)
                .count(10)
                .saleId(correctTestSale.get(2))
                .salePrice(100792.0)
                .description("iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone13ProMax.jpg")
                .build()
        );
        correctTestProducts.add(Product.builder()
                .productId(3)
                .nameProduct("Смартфон Apple iPhone 11")
                .price(53390.0)
                .count(20)
                .saleId(correctTestSale.get(1))
                .salePrice(48051.0)
                .description("iPhone 11 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone11.jpg")
                .build()
        );
        correctTestProducts.add(Product.builder()
                .productId(4)
                .nameProduct("Смартфон Samsung Galaxy S8")
                .price(11950.0)
                .count(13)
                .saleId(correctTestSale.get(0))
                .salePrice(11950.0)
                .description("Samsung Galaxy S8 - еще один из лидеров на рынке телефонов.")
                .road("SamsungGalaxyS8.jpg")
                .build()
        );
        correctTestProducts.add(Product.builder()
                .productId(5)
                .nameProduct("Наушники Apple AirPods Pro")
                .price(23999.0)
                .count(40)
                .saleId(correctTestSale.get(0))
                .salePrice(23999.0)
                .description("Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день.")
                .road("AirPodsPro.jpg")
                .build()
        );
        correctTestProducts.add(Product.builder()
                .productId(6)
                .nameProduct("Смартфон Huawei P50")
                .price(39999.0)
                .count(25)
                .saleId(correctTestSale.get(1))
                .salePrice(35991.0)
                .description("Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории.")
                .road("HuaweiP50.jpg")
                .build()
        );
    }

    @Test
    @DisplayName("When will the correct data for each product be returned")
    void findProductByProductId_ReturnsCorrectEntity() {
        for (Product p: correctTestProducts) {
            Optional<Product> product = productRepository.findProductByProductId(p.getProductId());
            assertNotEquals(Optional.empty(), product);
            product.ifPresent(value -> assertEquals(p, value));
        }
    }

    @Test
    @DisplayName("When looking for a non-existent product")
    void findProductByProductId_ReturnsOptionalEmpty() {
        Optional<Product> product = productRepository.findProductByProductId(Integer.MAX_VALUE);
        assertEquals(Optional.empty(), product);
    }

    @Test
    @DisplayName("When will all products be returned correctly")
    void findAll_ReturnCorrectResult() {
        Iterable<Product> products = productRepository.findAll();
        assertEquals(correctTestProducts, products);
    }

    @Test
    @DisplayName("When the product count update was successful")
    void updateProductCountByProductId() {
        productRepository.updateProductCountByProductId(correctTestProducts.get(0).getProductId(), Integer.MAX_VALUE);
        Optional<Product> updatedProduct = productRepository.findProductByProductId(correctTestProducts.get(0).getProductId());
        assertNotEquals(Optional.empty(), updatedProduct);
        updatedProduct.ifPresent(value -> assertNotEquals(correctTestProducts.get(0).getCount(), value.getCount()));
    }
}