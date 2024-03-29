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

    @Transactional
    @Modifying
    @Query("UPDATE Product p " +
            "SET p.count = :count WHERE p.productId = :productId")
    void updateProductCountByProductId(@Param("productId") Integer productId,
                                       @Param("count") Integer count);
}
