package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.service.product.ProductService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductListDto> findProductListDtoByProductId(@PathVariable("productId") Integer productId){
        try {
            return new ResponseEntity<>(productService.findProductListDtoByProductId(productId), HttpStatus.OK);
        }catch (ProductNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts(){
        try {
            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
        }catch (ProductNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
