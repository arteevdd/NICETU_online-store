package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.dto.ProductRequestDto;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.service.product.ProductService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/online-shop")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findProductByProductId(@PathVariable("productId") Integer productId){
        try {
            return new ResponseEntity<>(productService.findProductByProductId(productId), HttpStatus.OK);
        }catch (ProductNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAllProducts(){
        try {
            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
        }catch (ProductNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addNewProduct (@RequestBody ProductRequestDto productRequestDto){
        return new ResponseEntity<>(productService.addNewProduct(productRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/products/{productId}")
    public void updateProducerByProducerId(@PathVariable("productId") Integer productId, @RequestBody HashMap<String, Object> json){
        productService.updateProductByProductId(productId, (String) json.get("nameProduct"), (Double) json.get("price"), (Integer) json.get("count"));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProductByProductId(@PathVariable("productId") Integer productId){
        try {
            productService.deleteProductByProductId(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProductNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
