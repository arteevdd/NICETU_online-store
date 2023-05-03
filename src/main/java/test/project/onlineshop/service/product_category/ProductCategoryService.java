package test.project.onlineshop.service.product_category;

import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findProductCategoryByProductCategoryId(Integer productCategoryId);

    List<ProductCategory> findAll();

    List<ProductListDto> findProductCategoriesByCategoryId(Integer categoryId);
}
