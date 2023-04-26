package test.project.onlineshop.service.product_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.ProductCategory;
import test.project.onlineshop.exception.ProductCategoryNotFoundException;
import test.project.onlineshop.repository.ProductCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findProductCategoryByProductCategoryId(Integer productCategoryId) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findProductCategoryByProductCategoryId(productCategoryId);
        if (productCategory.isPresent()){
            return productCategory.get();
        }else {
            throw new ProductCategoryNotFoundException("Product category not found!");
        }
    }

    @Override
    public List<ProductCategory> findAll() {
        return (List<ProductCategory>) productCategoryRepository.findAll();
    }
}
