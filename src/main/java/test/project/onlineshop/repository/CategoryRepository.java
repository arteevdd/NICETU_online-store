package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT new test.project.onlineshop.dto.CategoryDto(c.parentCategoryId.categoryId, c.categoryId, c.nameCategory) FROM Category c")
    Iterable<CategoryDto> categoryTree();

    @Query("SELECT c FROM Category c " +
            "WHERE c.parentCategoryId.categoryId = :categoryId")
    Iterable<Category> findSubCategories(@Param("categoryId") Integer categoryId);
}
