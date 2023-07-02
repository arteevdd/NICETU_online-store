package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.entity.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@DisplayName("CRUD - methods: Category")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private List<CategoryDto> correctTestTree = new ArrayList<>();

    private List<Category> correctTestChildren = new ArrayList<>();

    private final int testRelatedProductId = 4;

    {
        correctTestTree.add(CategoryDto.builder()
                .categoryId(1)
                .parentCategoryId(null)
                .nameCategory("Смартфоны")
                .build()
        );
        correctTestTree.add(CategoryDto.builder()
                .categoryId(2)
                .parentCategoryId(1)
                .nameCategory("Apple")
                .build()
        );
        correctTestTree.add(CategoryDto.builder()
                .categoryId(3)
                .parentCategoryId(1)
                .nameCategory("Samsung")
                .build()
        );
        correctTestTree.add(CategoryDto.builder()
                .categoryId(4)
                .parentCategoryId(1)
                .nameCategory("Сопутствующие товары")
                .build()
        );
        correctTestTree.add(CategoryDto.builder()
                .categoryId(5)
                .parentCategoryId(4)
                .nameCategory("Наушники")
                .build()
        );

        correctTestChildren.add(Category.builder()
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
                .build()
        );
    }

    @Test
    @DisplayName("Return correct tree hierarchy")
    void categoryTree_ReturnsCorrectTree() {
        Iterable<CategoryDto> categories = categoryRepository.categoryTree();
        assertEquals(correctTestTree, categories);
    }

    @Test
    @DisplayName("Return correct children by parent_id")
    void findSubCategories() {
        Iterable<Category> children = categoryRepository.findSubCategories(testRelatedProductId);
        assertEquals(correctTestChildren, children);
    }
}