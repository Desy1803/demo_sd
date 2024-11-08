package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.UserRequest;
import com.example.demo_sd.entities.UserEntity;
import com.example.demo_sd.repositories.UserRepository;
import com.example.demo_sd.responses.KeycloakResponse;
import com.example.demo_sd.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/security")
@CrossOrigin()
public class KeycloakController {

    private final KeycloakService keycloakService;
    @Autowired
    private UserRepository userRepository;

    public KeycloakController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }


    @PostMapping("/register")
    public void registerUser(@RequestBody UserRequest user) {
        try{
            System.out.println(user);
            keycloakService.createUser(
                    user
            );

        }catch (Exception e){
            throw new RuntimeException("Couldn't save the user");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestParam String refreshToken) {
        return keycloakService.refreshToken(refreshToken, "spring");
    }

    @PostMapping("/login")
    public KeycloakResponse login(@RequestParam String username, @RequestParam String password) {
        try {
            KeycloakResponse response = keycloakService.loginUser(username, password);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new KeycloakResponse();
        }
    }



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setEmail(userDetails.getEmail());
                    user.setPhoneNumber(userDetails.getPhoneNumber());
                    user.setAddress(userDetails.getAddress());
                    user.setCity(userDetails.getCity());
                    user.setCountry(userDetails.getCountry());
                    user.setDateOfBirth(userDetails.getDateOfBirth());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam String username){
        keycloakService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try{
            keycloakService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            //return userRepository.findById(id)
                  //  .map(user -> {
                    //    userRepository.delete(user);
                      //  return ResponseEntity.ok().build();
                    //})
                    //.orElse(ResponseEntity.notFound().build());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
