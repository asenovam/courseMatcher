/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.daos.UserDao;
import co.uk.city.london.entities.UserEntity;
import com.google.gson.Gson;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mar_9
 */
@RestController
public class UserRestController {
    
    @Autowired
    private UserDao userDao; 
    
    // so that when u are calling /users it will load all users in a JSON
    @GetMapping("/users")
    public String users() {
        List<UserEntity> allUsers = userDao.findAll();
        
        Gson gson = new Gson();
        
        String usersAsJson = gson.toJson(allUsers);
        
        return usersAsJson;
    }
    
    @PostMapping
    public ResponseEntity<String> crateUser(@RequestBody UserEntity user) {
         UserEntity    anotherUser = userDao.findByEmail(user.getEmail());
         if (anotherUser != null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is already a user with that email");
         }
         
        this.userDao.save(user);
        
        return  ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
    
}
