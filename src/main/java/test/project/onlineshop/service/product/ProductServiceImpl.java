package test.project.onlineshop.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.dto.ProductRequestDto;
import test.project.onlineshop.entity.Product;
import test.project.onlineshop.exception.ProductNotFoundException;
import test.project.onlineshop.repository.ProducerRepository;
import test.project.onlineshop.repository.ProductRepository;
import test.project.onlineshop.repository.SaleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProducerRepository producerRepository;

    private final SaleRepository saleRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProducerRepository producerRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
        this.saleRepository = saleRepository;
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

    @Override
    public Product addNewProduct(ProductRequestDto productRequestDto) {
//        TODO: Написать обработку значений
        Integer percent = saleRepository.findById(productRequestDto.getSaleId()).get().getSalePercent();
        return productRepository.save(
                new Product(
                        productRequestDto.getNameProduct(),
                        productRequestDto.getPrice(),
                        productRequestDto.getCount(),
                        productRequestDto.getDescription(),
                        productRequestDto.getPrice() * percent / 100,
                        producerRepository.findProducerByProducerId(productRequestDto.getProducerId()).get(),
                        saleRepository.findById(productRequestDto.getSaleId()).get()
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
