package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.exception.ProductCategoryNotFoundException;
import test.project.onlineshop.service.product_category.ProductCategoryService;

import java.util.List;

@RestController
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/product_category/{categoryId}")
    public ResponseEntity<List<ProductListDto>> relationshipsBetweenCategoryAndProduct(@PathVariable("categoryId") Integer categoryId){
        try {
            return new ResponseEntity<>(productCategoryService.findProductCategoriesByCategoryId(categoryId), HttpStatus.OK);
        }catch (ProductCategoryNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
