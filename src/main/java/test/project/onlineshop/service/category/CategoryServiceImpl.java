package test.project.onlineshop.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.Category;
import test.project.onlineshop.exception.CategoryNotFoundException;
import test.project.onlineshop.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findCategoryByCategoryId(Integer categoryId) {
        Optional<Category> category = categoryRepository.findCategoryByCategoryId(categoryId);
        if (category.isPresent()){
            return category.get();
        }else {
            throw new CategoryNotFoundException("Category not found!");
        }
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }
}
