package test.project.onlineshop.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.exception.CategoryNotFoundException;
import test.project.onlineshop.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> categoryTree() {
        List<CategoryDto> categoryTree = (List<CategoryDto>) categoryRepository.categoryTree();
        if (categoryTree.isEmpty()){
            throw new CategoryNotFoundException("Categories not found");
        }else {
            return categoryTree;
        }
    }
}
