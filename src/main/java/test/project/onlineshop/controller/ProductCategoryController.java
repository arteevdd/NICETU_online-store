package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.entity.ProductCategory;
import test.project.onlineshop.exception.ProductCategoryNotFoundException;
import test.project.onlineshop.service.product_category.ProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("/online-shop")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/pc/{productCategoryId}")
    public ResponseEntity<ProductCategory> findProductCategoryByProductCategoryId(@PathVariable("productCategoryId") Integer productCategoryId){
        try {
            return new ResponseEntity<>(productCategoryService.findProductCategoryByProductCategoryId(productCategoryId), HttpStatus.OK);
        }catch (ProductCategoryNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/pc")
    public ResponseEntity<List<ProductCategory>> findAllProductCategories(){
        try {
            return new ResponseEntity<>(productCategoryService.findAll(), HttpStatus.OK);
        }catch (ProductCategoryNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
