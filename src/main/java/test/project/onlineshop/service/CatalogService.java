package test.project.onlineshop.service;

import test.project.onlineshop.entity.Catalog;

public interface CatalogService {

    Catalog findCatalogByCatalogId(Integer id);
}
