package test.project.onlineshop.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductListDto findProductListDtoByProductId(Integer productId) {
        Optional<ProductListDto> product = productRepository.findProductListDtoByProductId(productId);
        if (product.isPresent()){
            return product.get();
        }else {
            throw new ProductNotFoundException("Product not found!");
        }
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }
}
