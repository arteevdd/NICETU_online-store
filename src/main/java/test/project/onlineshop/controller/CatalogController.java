package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.entity.Catalog;
import test.project.onlineshop.exception.CatalogNotFoundException;
import test.project.onlineshop.service.CatalogService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/online-shop")
public class CatalogController {

    private CatalogService catalogService;

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalogs/{id}")
    public ResponseEntity<Catalog> findCatalogByCatalogId(@PathVariable("id") Integer id){
        try {
            return new ResponseEntity<>(catalogService.findCatalogByCatalogId(id), HttpStatus.OK);
        }catch (CatalogNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<Catalog>> findAllCatalogs(){
        try {
            return new ResponseEntity<>(catalogService.findAll(), HttpStatus.OK);
        }catch (CatalogNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/catalogs",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Catalog> addNewCatalog(@RequestBody Catalog catalog){
        return new ResponseEntity<>(catalogService.addNewCatalog(catalog), HttpStatus.CREATED);
    }

    @PutMapping("/catalogs/{id}")
    public void updateNameCatalogByCatalogId(@PathVariable("id")  Integer id, @RequestBody Map<String, Object> json){
        catalogService.updateNameCatalogByCatalogId(id, (String) json.get("name_catalog"));
    }

    @DeleteMapping("/catalogs/{nameCatalog}")
    public ResponseEntity<Catalog> deleteCatalogByNameCatalog(@PathVariable("nameCatalog") String nameCatalog){
        try {
            catalogService.deleteCatalogByName(nameCatalog);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CatalogNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
