package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Sale;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Integer> {
}
