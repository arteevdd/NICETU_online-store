package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import test.project.onlineshop.entity.Sale;

public interface SaleRepository extends CrudRepository<Sale, Integer> {
}
