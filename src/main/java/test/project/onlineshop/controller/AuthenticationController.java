package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.project.onlineshop.dto.AuthRequest;
import test.project.onlineshop.dto.UserRequest;
import test.project.onlineshop.entity.Sale;
import test.project.onlineshop.repository.SaleRepository;
import test.project.onlineshop.service.user.UserService;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8081")
public class AuthenticationController {

    private final UserService userService;

    private final SaleRepository saleRepository;

    @Autowired
    public AuthenticationController(UserService userService, SaleRepository saleRepository) {
        this.userService = userService;
        this.saleRepository = saleRepository;
    }

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> registration(@RequestBody UserRequest user) {
        return new ResponseEntity<>(userService.registration(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userService.login(authRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/test")
    public ResponseEntity test(@RequestBody Integer value){
        Sale sale = saleRepository.save(new Sale(value));
        if (sale != null){
            return ResponseEntity.ok("ok");
        }else {
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
    }
}
