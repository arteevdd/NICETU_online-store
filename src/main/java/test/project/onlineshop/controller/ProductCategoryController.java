package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.exception.ProductCategoryNotFoundException;
import test.project.onlineshop.service.product_category.ProductCategoryService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/product_category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductListDto>> relationshipsBetweenCategoryAndProduct(@PathVariable("categoryId") Integer categoryId){
        try {
            return new ResponseEntity<>(productCategoryService.findProductCategoriesByCategoryId(categoryId), HttpStatus.OK);
        }catch (ProductCategoryNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
