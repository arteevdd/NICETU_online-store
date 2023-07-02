package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("CRUD - methods: Product category")
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private final List<ProductListDto> correctTestProductsReceivedFromAppleCategory = new ArrayList<>();

    private final List<ProductListDto> correctTestProductsReceivedFromSmartPhoneCategory = new ArrayList<>();

    {
        correctTestProductsReceivedFromAppleCategory.add(ProductListDto.builder()
                .productId(1)
                .nameProduct("Смартфон Apple iPhone 14")
                .price(86990.0)
                .count(15)
                .salePrice(78291.0)
                .description("iPhone 14 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone14.jpg")
                .build()
        );
        correctTestProductsReceivedFromAppleCategory.add(ProductListDto.builder()
                .productId(2)
                .nameProduct("Смартфон Apple iPhone 13 Pro Max")
                .price(125990.0)
                .count(10)
                .salePrice(100792.0)
                .description("iPhone 13 Pro Max - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone13ProMax.jpg")
                .build()
        );
        correctTestProductsReceivedFromAppleCategory.add(ProductListDto.builder()
                .productId(3)
                .nameProduct("Смартфон Apple iPhone 11")
                .price(53390.0)
                .count(20)
                .salePrice(48051.0)
                .description("iPhone 11 - смартфон, который является одним из лучших в своем классе.")
                .road("iPhone11.jpg")
                .build()
        );
        correctTestProductsReceivedFromSmartPhoneCategory.add(ProductListDto.builder()
                .productId(6)
                .nameProduct("Смартфон Huawei P50")
                .price(39999.0)
                .count(25)
                .salePrice(35991.0)
                .description("Huawei P40 Pro - устройство этого производителя можно отнести к топ-категории.")
                .road("HuaweiP50.jpg")
                .build()
        );
    }

    @Test
    @DisplayName("Will returns all products relationship to category Apple")
    void findProductCategoriesByCategoryId_ReturnsAllProductsRelationshipToApple() {
        int testAppleCategoryId = 2;
        Iterable<ProductListDto> products = productCategoryRepository.findProductCategoriesByCategoryId(testAppleCategoryId);
        assertEquals(correctTestProductsReceivedFromAppleCategory, products);
    }

    @Test
    @DisplayName("Will returns all products relationship to category Smartphone")
    void findProductCategoriesByCategoryId_ReturnsAllProductsRelationshipToSmartphone() {
        int testSmartphoneCategoryId = 1;
        Iterable<ProductListDto> products = productCategoryRepository.findProductCategoriesByCategoryId(testSmartphoneCategoryId);
        assertEquals(correctTestProductsReceivedFromSmartPhoneCategory, products);
    }
}