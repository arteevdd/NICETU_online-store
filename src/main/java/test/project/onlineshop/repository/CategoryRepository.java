package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Optional<Category> findCategoryByCategoryId(Integer categoryId);

    @Query("SELECT c.categoryId, c.nameCategory FROM Category c " +
            "WHERE c.parentCategoryId.categoryId = :parentCategoryId")
    Iterable<Category> findCategoryByParentCategoryId(@Param("parentCategoryId") Integer parentCategoryId);

    @Query("SELECT new test.project.onlineshop.dto.CategoryDto(c.parentCategoryId.categoryId, c.categoryId, c.nameCategory) FROM Category c")
    Iterable<CategoryDto> findAllCustom();
}
