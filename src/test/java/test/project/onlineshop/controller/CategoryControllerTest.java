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
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.exception.CategoryNotFoundException;
import test.project.onlineshop.service.category.CategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Web layer: Category")
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("When controller return correct category tree entity")
    void categoryTree_ReturnsCorrectTreeEntity() {
        List<CategoryDto> expectedTree = new ArrayList<>();
        expectedTree.add(CategoryDto.builder()
                .categoryId(1)
                .parentCategoryId(null)
                .nameCategory("Смартфоны")
                .build()
        );
        expectedTree.add(CategoryDto.builder()
                .categoryId(2)
                .parentCategoryId(1)
                .nameCategory("Apple")
                .build()
        );
        expectedTree.add(CategoryDto.builder()
                .categoryId(3)
                .parentCategoryId(1)
                .nameCategory("Samsung")
                .build()
        );
        expectedTree.add(CategoryDto.builder()
                .categoryId(4)
                .parentCategoryId(1)
                .nameCategory("Сопутствующие товары")
                .build()
        );
        expectedTree.add(CategoryDto.builder()
                .categoryId(5)
                .parentCategoryId(4)
                .nameCategory("Наушники")
                .build()
        );

        when(categoryService.categoryTree()).thenReturn(expectedTree);

        var responseEntity = categoryController.categoryTree();

        assertNotNull(responseEntity);
        assertEquals(expectedTree, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("When the empty category tree returned")
    void categoryTree_ReturnsEmptyList_TrowsResponseStatusException_NotFound() {
        when(categoryService.categoryTree()).thenThrow(CategoryNotFoundException.class);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> {
                    categoryController.categoryTree();
                });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}