package test.project.onlineshop.service.product;

import test.project.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {

    Product findProductByProductId(Integer productId);

    List<Product> findAll();
}
