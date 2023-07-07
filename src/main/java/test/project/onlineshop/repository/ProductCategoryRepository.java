package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.ProductCategory;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {

    @Query("SELECT new test.project.onlineshop.dto.ProductListDto(p.productId, p.nameProduct, p.price, p.count, p.description, p.salePrice, p.road) FROM ProductCategory pc " +
            "JOIN Category cat ON pc.categoryId = cat " +
            "JOIN Product p ON pc.productId = p " +
            "WHERE cat.categoryId = :categoryId")
    Iterable<ProductListDto> findProductCategoriesByCategoryId(@Param("categoryId") Integer categoryId);
}
