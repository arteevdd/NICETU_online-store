package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.CategoryDto;
import test.project.onlineshop.entity.Category;
import test.project.onlineshop.exception.CategoryNotFoundException;
import test.project.onlineshop.service.category.CategoryService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/online-shop")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> findCategoryByCategoryId(@PathVariable("categoryId") Integer categoryId){
        try {
            return new ResponseEntity<>(categoryService.findCategoryByCategoryId(categoryId), HttpStatus.OK);
        }catch (CategoryNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> findAllCustom(){
        try {
            return new ResponseEntity<>(categoryService.findAllCustom(), HttpStatus.OK);
        }catch (CategoryNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}