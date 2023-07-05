package test.project.onlineshop.service.product_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Category;
import test.project.onlineshop.exception.ProductCategoryNotFoundException;
import test.project.onlineshop.repository.CategoryRepository;
import test.project.onlineshop.repository.ProductCategoryRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private final ProductCategoryRepository productCategoryRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository, CategoryRepository categoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductListDto> findProductCategoriesByCategoryId(Integer categoryId) {
        List<List<ProductListDto>> productCategoryList = new ArrayList<>();
        List<ProductListDto> closestProduct = (List<ProductListDto>) productCategoryRepository.findProductCategoriesByCategoryId(categoryId);
        if (!closestProduct.isEmpty()) {
            productCategoryList.add(closestProduct);
        }
        List<Category> categories = (List<Category>) categoryRepository.findSubCategories(categoryId);
        if (!categories.isEmpty()) {
            for (Category category : categories) {
                addProductsByCategory(category, productCategoryList);
            }
        }
        if (productCategoryList.isEmpty()) {
            throw new ProductCategoryNotFoundException("Products with categoryId = " + categoryId + " not found!");
        }else {
            return productCategoryList.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    public void addProductsByCategory(Category category, List<List<ProductListDto>> productCategoryList){
        List<Category> tempCategory = (List<Category>) categoryRepository.findSubCategories(category.getCategoryId());
        if (tempCategory.isEmpty()) {
            productCategoryList.add((List<ProductListDto>) productCategoryRepository.findProductCategoriesByCategoryId(category.getCategoryId()));
        }else {
            for (Category elemCategory : tempCategory) {
                addProductsByCategory(elemCategory, productCategoryList);
            }
        }
    }
}
