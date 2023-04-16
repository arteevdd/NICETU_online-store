package test.project.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;
import test.project.onlineshop.entity.Catalog;

import java.util.Optional;

public interface CatalogRepository extends CrudRepository<Catalog, Integer> {

    Optional<Catalog> findCatalogByCatalogId(Integer integer);
}
