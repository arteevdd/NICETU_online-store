package test.project.onlineshop.service.category;

import test.project.onlineshop.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findCategoryByCategoryId(Integer categoryId);

    List<Category> findAll();
}
