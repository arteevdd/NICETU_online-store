package test.project.onlineshop.service.product_category;

import test.project.onlineshop.dto.ProductListDto;

import java.util.List;

public interface ProductCategoryService {

    List<ProductListDto> findProductCategoriesByCategoryId(Integer categoryId);
}
