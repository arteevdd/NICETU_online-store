package test.project.onlineshop.service;

import test.project.onlineshop.entity.Catalog;

import java.util.List;

public interface CatalogService {

    Catalog findCatalogByCatalogId(Integer id);

    List<Catalog> findAll();

    Catalog addNewCatalog(Catalog catalog);

    void updateNameCatalogByCatalogId(Integer catalogId, String nameCatalog);

    void deleteCatalogByName(String nameCatalog);
}
