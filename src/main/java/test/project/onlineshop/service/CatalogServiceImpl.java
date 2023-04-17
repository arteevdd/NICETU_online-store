package test.project.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.Catalog;
import test.project.onlineshop.exception.CatalogNotFoundException;
import test.project.onlineshop.repository.CatalogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Catalog findCatalogByCatalogId(Integer id){
        Optional<Catalog> catalog = catalogRepository.findCatalogByCatalogId(id);
        if (catalog.isPresent()){
            return catalog.get();
        }else {
            throw new CatalogNotFoundException("Catalog not found!");
        }
    }

    @Override
    public List<Catalog> findAll() {
        return (List<Catalog>) catalogRepository.findAll();
    }

    @Override
    public Catalog addNewCatalog(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public void updateNameCatalogByCatalogId(Integer catalogId, String nameCatalog) {
        catalogRepository.updateNameCatalogByCatalogId(catalogId, nameCatalog);
    }

    @Override
    public void deleteCatalogByName(String nameCatalog) {
        catalogRepository.deleteCatalogByNameCatalog(nameCatalog);
    }
}
