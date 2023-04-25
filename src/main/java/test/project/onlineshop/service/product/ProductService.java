package test.project.onlineshop.service.product;

import test.project.onlineshop.dto.ProductDto;
import test.project.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {

    Product findProductByProductId(Integer productId);

    List<Product> findAll();

    Product addNewProduct(ProductDto productDto);

    void updateProductByProductId(Integer productId, String nameProduct, Double price, Integer count);

    void deleteProductByProductId(Integer productId);
}
