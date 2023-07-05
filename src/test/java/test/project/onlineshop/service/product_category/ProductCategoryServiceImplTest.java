package test.project.onlineshop.service.product_category;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Category;
import test.project.onlineshop.repository.CategoryRepository;
import test.project.onlineshop.repository.ProductCategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer: Product category")
class ProductCategoryServiceImplTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    @DisplayName("When all products of the main category will be correctly displayed")
    void findProductCategoriesByCategoryId_ReturnsAllProductsForMainCategory() {
        Category mainCategory = Category.builder()
                .categoryId(1)
                .parentCategoryId(null)
                .nameCategory("Смартфоны")
                .build();

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

        List<Category> closestCategory = new ArrayList<>();
        closestCategory.add(Category.builder()
                .categoryId(2)
                .parentCategoryId(mainCategory)
                .nameCategory("Apple")
                .build()
        );
        closestCategory.add(Category.builder()
                .categoryId(3)
                .parentCategoryId(mainCategory)
                .nameCategory("Samsung")
                .build()
        );
        closestCategory.add(Category.builder()
                .categoryId(4)
                .parentCategoryId(mainCategory)
                .nameCategory("Сопутствующие товары")
                .build()
        );

        Category subSubCategory = Category.builder()
                .categoryId(5)
                .parentCategoryId(closestCategory.get(2))
                .nameCategory("Наушники")
                .build();

        // huawei
        when(productCategoryRepository.findProductCategoriesByCategoryId(mainCategory.getCategoryId())).thenReturn(Arrays.asList(expected.get(0)));
        when(categoryRepository.findSubCategories(mainCategory.getCategoryId())).thenReturn(closestCategory);
        // apple
        when(categoryRepository.findSubCategories(closestCategory.get(0).getCategoryId())).thenReturn(Collections.emptyList());
        when(productCategoryRepository.findProductCategoriesByCategoryId(closestCategory.get(0).getCategoryId())).thenReturn(Arrays.asList(expected.get(1), expected.get(2), expected.get(3)));
        // samsung
        when(categoryRepository.findSubCategories(closestCategory.get(1).getCategoryId())).thenReturn(Collections.emptyList());
        when(productCategoryRepository.findProductCategoriesByCategoryId(closestCategory.get(1).getCategoryId())).thenReturn(Arrays.asList(expected.get(4)));

        // Сопутствующие товары
        when(categoryRepository.findSubCategories(closestCategory.get(2).getCategoryId())).thenReturn(Arrays.asList(subSubCategory));
        when(categoryRepository.findSubCategories(subSubCategory.getCategoryId())).thenReturn(Collections.emptyList());
        when(productCategoryRepository.findProductCategoriesByCategoryId(subSubCategory.getCategoryId())).thenReturn(Arrays.asList(expected.get(5)));

        List<ProductListDto> actual = productCategoryService.findProductCategoriesByCategoryId(mainCategory.getCategoryId());

        assertEquals(expected, actual);
        verify(productCategoryRepository, times(1)).findProductCategoriesByCategoryId(mainCategory.getCategoryId());
        verify(categoryRepository, times(1)).findSubCategories(mainCategory.getCategoryId());

        verify(categoryRepository, times(1)).findSubCategories(closestCategory.get(0).getCategoryId());
        verify(productCategoryRepository, times(1)).findProductCategoriesByCategoryId(closestCategory.get(0).getCategoryId());

        verify(categoryRepository, times(1)).findSubCategories(closestCategory.get(1).getCategoryId());
        verify(productCategoryRepository, times(1)).findProductCategoriesByCategoryId(closestCategory.get(1).getCategoryId());

        verify(categoryRepository, times(1)).findSubCategories(closestCategory.get(2).getCategoryId());
        verify(categoryRepository, times(1)).findSubCategories(subSubCategory.getCategoryId());
        verify(productCategoryRepository, times(1)).findProductCategoriesByCategoryId(subSubCategory.getCategoryId());
    }

    @Test
    @DisplayName("When an empty List returns")
    void findProductCategoriesByCategoryId_ReturnsEmptyList() {
        when(productCategoryRepository.findProductCategoriesByCategoryId(anyInt())).thenReturn(Collections.emptyList());
        when(categoryRepository.findSubCategories(anyInt())).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), productCategoryService.findProductCategoriesByCategoryId(anyInt()));
    }

    @Test
    @DisplayName("When the method will be called once")
    void addProductsByCategory_WillBeCalledOnce() {
        List<List<ProductListDto>> productCategoryList = new ArrayList<>();

        List<List<ProductListDto>> expectedProductCategoryList = new ArrayList<>();

        Category mainCategory = Category.builder()
                .categoryId(5)
                .parentCategoryId(Category.builder()
                        .categoryId(4)
                        .parentCategoryId(Category.builder()
                                .categoryId(1)
                                .parentCategoryId(null)
                                .nameCategory("Смартфоны")
                                .build())
                        .nameCategory("Сопутствующие товары")
                        .build())
                .nameCategory("Наушники")
                .build();

        List<ProductListDto> products = new ArrayList<>();
        products.add(ProductListDto.builder()
                .nameProduct("Наушники Apple AirPods Pro")
                .price(23999.0)
                .count(40)
                .salePrice(23999.0)
                .description("Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день.")
                .road("AirPodsPro.jpg")
                .build()
        );

        expectedProductCategoryList.add(products);

        when(categoryRepository.findSubCategories(mainCategory.getCategoryId())).thenReturn(Collections.emptyList());
        when(productCategoryRepository.findProductCategoriesByCategoryId(mainCategory.getCategoryId())).thenReturn(products);

        productCategoryService.addProductsByCategory(mainCategory, productCategoryList);

        assertEquals(expectedProductCategoryList, productCategoryList);
        verify(categoryRepository, times(1)).findSubCategories(mainCategory.getCategoryId());
        verify(productCategoryRepository, times(1)).findProductCategoriesByCategoryId(mainCategory.getCategoryId());
    }

    @Test
    @DisplayName("When the method is called twice")
    void addProductsByCategory_WillBeTwice() {
        List<List<ProductListDto>> productCategoryList = new ArrayList<>();

        List<List<ProductListDto>> expectedProductCategoryList = new ArrayList<>();

        Category mainCategory = Category.builder()
                .categoryId(4)
                .parentCategoryId(Category.builder()
                        .categoryId(1)
                        .parentCategories(null)
                        .nameCategory("Смартфоны").build())
                .nameCategory("Сопутствующие товары")
                .build();

        List<Category> subCategoriesList = new ArrayList<>();
        Category subCategory = Category.builder()
                .categoryId(5)
                .parentCategoryId(mainCategory)
                .nameCategory("Наушники")
                .build();
        subCategoriesList.add(subCategory);

        List<ProductListDto> products = new ArrayList<>();
        products.add(ProductListDto.builder()
                .nameProduct("Наушники Apple AirPods Pro")
                .price(23999.0)
                .count(40)
                .salePrice(23999.0)
                .description("Наушники AirPods Pro — это активное шумоподавление для иммерсивного звука и комфорт от использования целый день.")
                .road("AirPodsPro.jpg")
                .build());

        expectedProductCategoryList.add(products);

        when(categoryRepository.findSubCategories(mainCategory.getCategoryId())).thenReturn(subCategoriesList);
        when(categoryRepository.findSubCategories(subCategory.getCategoryId())).thenReturn(Collections.emptyList());
        when(productCategoryRepository.findProductCategoriesByCategoryId(subCategory.getCategoryId())).thenReturn(products);

        productCategoryService.addProductsByCategory(mainCategory, productCategoryList);

        assertEquals(expectedProductCategoryList, productCategoryList);
        verify(categoryRepository, times(1)).findSubCategories(mainCategory.getCategoryId());
        verify(categoryRepository, times(1)).findSubCategories(subCategory.getCategoryId());
        verify(productCategoryRepository, times(1)).findProductCategoriesByCategoryId(subCategory.getCategoryId());
    }
}