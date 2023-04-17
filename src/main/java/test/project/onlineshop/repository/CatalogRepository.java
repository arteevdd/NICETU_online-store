package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Catalog;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Integer> {

    Optional<Catalog> findCatalogByCatalogId(Integer integer);

    Optional<Catalog> findCatalogByNameCatalog(String nameCatalog);

    Iterable<Catalog> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE Catalog c " +
            "SET c.nameCatalog = :nameCatalog " +
            "WHERE c.catalogId = :catalogId")
    void updateNameCatalogByCatalogId(@Param("catalogId") Integer catalogId,
                                  @Param("nameCatalog") String nameCatalog);

    @Transactional
    void deleteCatalogByNameCatalog(String nameCatalog);

}
