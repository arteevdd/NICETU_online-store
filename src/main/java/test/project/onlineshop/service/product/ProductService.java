package test.project.onlineshop.service.product;

import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.dto.ProductRequestDto;
import test.project.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {

    ProductListDto findProductListDtoByProductId(Integer productId);

    List<Product> findAll();

    Product addNewProduct(ProductRequestDto productRequestDto);

    void updateProductByProductId(Integer productId, String nameProduct, Double price, Integer count);

    void deleteProductByProductId(Integer productId);
}
