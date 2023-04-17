package test.project.onlineshop.repository;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.project.onlineshop.entity.Catalog;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@DisplayName("CRUD - methods: Catalog")
class CatalogRepositoryTest {

    @Autowired
    private CatalogRepository catalogRepository;

    @Test
    @Order(1)
    @DisplayName("Save and select new catalog")
    public void saveCatalog(){
        final String nameCatalog = "saveTest";
        catalogRepository.save(new Catalog(nameCatalog));
        assertEquals(nameCatalog, catalogRepository.findCatalogByNameCatalog(nameCatalog).get().getNameCatalog());
    }

    @Test
    @Order(2)
    @DisplayName("Select all catalogs")
    public void findAllCatalogs(){
        List<Catalog> catalogs = (List<Catalog>) catalogRepository.findAll();

        assertEquals("Смартфоны", catalogs.get(0).getNameCatalog());
        assertEquals("Аудиотехника", catalogs.get(1).getNameCatalog());
//        equal
//        assertEquals(2, catalogs.size());
    }

    @Test
    @Order(3)
    @DisplayName("Remove catalog")
    public void deleteCatalog(){
        final String nameCatalog = "saveTest2";
        catalogRepository.save(new Catalog(nameCatalog));
        catalogRepository.deleteCatalogByNameCatalog(nameCatalog);
        Optional<Catalog> catalog = catalogRepository.findCatalogByNameCatalog(nameCatalog);
        assertEquals(Optional.empty(), catalog);
    }

    @Test
    @Order(4)
    @DisplayName("Select catalog by id")
    public void findCatalogById(){
        assertEquals("Смартфоны", catalogRepository.findCatalogByCatalogId(1).get().getNameCatalog());
    }

    @Test
    @Order(5)
    @DisplayName("Update name catalog")
    public void updateNameCatalog(){
        final String newCatalogName = "newName";
        catalogRepository.updateNameCatalogByCatalogId(1, newCatalogName);
        assertEquals(newCatalogName, catalogRepository.findCatalogByCatalogId(1).get().getNameCatalog());
    }
}