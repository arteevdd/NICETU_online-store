package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.dto.ProductListDto;
import test.project.onlineshop.entity.Product;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findProductByProductId(Integer productId);


    @Query("SELECT new test.project.onlineshop.dto.ProductListDto(p.productId, p.nameProduct, p.price, p.count, p.description, p.salePrice, pr.nameProducer) FROM Product p " +
            "JOIN Producer pr ON p.producerId = pr " +
            "WHERE p.productId = :productId")
    Optional<ProductListDto> findProductListDtoByProductId(@Param("productId") Integer productId);

    Iterable<Product> findAll();


    @Transactional
    @Modifying
    @Query("UPDATE Product p " +
            "SET p.nameProduct = :nameProduct, p.price = :price, p.count = :count " +
            "WHERE p.productId = :productId")
    void updateProductByProductId(@Param("productId") Integer productId,
                                  @Param("nameProduct") String nameProduct,
                                  @Param("price") Double price,
                                  @Param("count") Integer count);

    @Transactional
    @Modifying
    @Query("UPDATE Product p " +
            "SET p.count = :count WHERE p.productId = :productId")
    void updateProductCountByProductId(@Param("productId") Integer productId,
                                       @Param("count") Integer count);

    @Transactional
    void deleteProductByProductId(Integer productId);
}
