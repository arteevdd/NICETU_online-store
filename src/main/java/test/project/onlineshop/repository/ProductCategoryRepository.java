package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.ProductCategory;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {

    Optional<ProductCategory> findProductCategoryByProductCategoryId(Integer categoryId);

    Iterable<ProductCategory> findAll();
}
