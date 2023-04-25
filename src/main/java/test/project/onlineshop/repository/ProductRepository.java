package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Product;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findProductByProductId(Integer productId);

    Iterable<Product> findAll();

    Optional<Product> findProductByNameProduct(String nameProduct);

    @Transactional
    @Modifying
    @Query("UPDATE Product " +
            "SET nameProduct = :nameProduct, price = :price, count = :count " +
            "WHERE productId = :productId")
    void updateProductByProductId(@Param("productId") Integer productId,
                                  @Param("nameProduct") String nameProduct,
                                  @Param("price") Double price,
                                  @Param("count") Integer count);

    @Transactional
    void deleteProductByProductId(Integer productId);
}
