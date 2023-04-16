package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.entity.Catalog;
import test.project.onlineshop.exception.CatalogNotFoundException;
import test.project.onlineshop.service.CatalogService;

@RestController
@RequestMapping("/online-shop")
public class CatalogController {

    private CatalogService catalogService;

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<Catalog> findCatalogByCatalogId(@PathVariable("id") Integer id){
        try {
            return new ResponseEntity<>(catalogService.findCatalogByCatalogId(id), HttpStatus.OK);
        }catch (CatalogNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
