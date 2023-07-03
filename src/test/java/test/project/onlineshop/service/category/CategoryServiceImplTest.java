package test.project.onlineshop.service.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer: Category")
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("When returns the correct category tree")
    void categoryTree_ReturnCorrectEntity() {
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

        when(categoryRepository.categoryTree()).thenReturn(expectedTree);

        assertEquals(expectedTree, categoryService.categoryTree());
        verify(categoryRepository, times(1)).categoryTree();
    }
}