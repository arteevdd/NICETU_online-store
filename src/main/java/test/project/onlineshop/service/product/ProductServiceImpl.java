package test.project.onlineshop.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.ProductDto;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.repository.ProducerRepository;
import test.project.onlineshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProducerRepository producerRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProducerRepository producerRepository) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
    }

    @Override
    public Product findProductByProductId(Integer productId) {
        Optional<Product> product = productRepository.findProductByProductId(productId);
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

    @Override
    public Product addNewProduct(ProductDto productDto) {
        return productRepository.save(
                new Product(
                        productDto.getNameProduct(),
                        productDto.getPrice(),
                        productDto.getCount(),
                        producerRepository.findProducerByProducerId(productDto.getProducerId()).get()
                )
        );
    }

    @Override
    public void updateProductByProductId(Integer productId, String nameProduct, Double price, Integer count) {
        productRepository.updateProductByProductId(productId, nameProduct, price, count);
    }

    @Override
    public void deleteProductByProductId(Integer productId) {
        productRepository.deleteProductByProductId(productId);
    }
}
