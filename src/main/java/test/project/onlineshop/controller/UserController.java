package test.project.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.project.onlineshop.entity.User;
import test.project.onlineshop.exception.UserNotFoundException;
import test.project.onlineshop.service.user.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/online-shop")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> findUserByUserId(@PathVariable("userId") Integer userId){
        try {
            return new ResponseEntity<>(userService.findUserByUserId(userId), HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(){
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        return new ResponseEntity<>(userService.addNewUser(user), HttpStatus.CREATED);
    }

//    TODO: Посмотреть best practice по назначению URL!
//    @PutMapping("/users/{userId}")
//    public void updateUserInitials(@PathVariable("userId") Integer userId, @RequestBody Map<String, String> json){
//        userService.updateUserInitials(userId, json.get("firstName"), json.get("secondName"));
//    }

    @PutMapping("/users/{userId}")
    public void updateUserEmail(@PathVariable("userId") Integer userId, @RequestBody Map<String, String> json){
        userService.updateEmail(userId, json.get("email"));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<User> deleteUserByUserId(@PathVariable("userId") Integer userId){
        try {
            userService.deleteUserByUserId(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
