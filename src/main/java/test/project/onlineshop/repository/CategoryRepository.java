package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Optional<Category> findCategoryByCategoryId(Integer categoryId);

    Iterable<Category> findAll();

}
