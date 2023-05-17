package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.entity.Producer;
import test.project.onlineshop.exception.ProducerNotFoundException;
import test.project.onlineshop.service.producer.ProducerService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/online-shop")
public class ProducerController {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/producers/{producerId}")
    public ResponseEntity<Producer> findProducerByProducerId(@PathVariable("producerId") Integer producerId){
        try {
            return new ResponseEntity<>(producerService.findProducerByProducerId(producerId), HttpStatus.OK);
        }catch (ProducerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/producers")
    public ResponseEntity<List<Producer>> findAllProducers(){
        try {
            return new ResponseEntity<>(producerService.findAll(), HttpStatus.OK);
        }catch (ProducerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/producers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producer> addNewProducer (@RequestBody Producer producer){
        return new ResponseEntity<>(producerService.addNewProducer(producer), HttpStatus.CREATED);
    }

    @PutMapping("/producers/{producerId}")
    public void updateProducerByProducerId(@PathVariable("producerId") Integer producerId, @RequestBody HashMap<String, String> json){
        producerService.updateNameProducerByProducerId(producerId, json.get("nameProducer"));
    }

    @DeleteMapping("/producers/{producerId}")
    public ResponseEntity<Producer> deleteProducerByProducerId (@PathVariable("producerId") Integer producerId){
        try {
            producerService.deleteProducerByProducerId(producerId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProducerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
