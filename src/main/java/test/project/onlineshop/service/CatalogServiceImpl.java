package test.project.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.Catalog;
import test.project.onlineshop.exception.CatalogNotFoundException;
import test.project.onlineshop.repository.CatalogRepository;

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
}
