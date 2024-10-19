package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.UserRequest;
import com.example.demo_sd.entities.UserEntity;
import com.example.demo_sd.repositories.UserRepository;
import com.example.demo_sd.services.KeycloakService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users/security")
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
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPassword()
            );

        }catch (Exception e){
            throw new RuntimeException("Couldn't save the user");
        }
    }


    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserRequest user) {
        try{
            System.out.println(user);
            UserEntity savedUser = userRepository.save(user.toModel(user));
            return ResponseEntity.ok(savedUser);

        }catch (Exception e){
            throw new RuntimeException("Couldn't save the user");
        }
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
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
                    user.setUpdatedAt(LocalDateTime.now());
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

    @PutMapping("/assignRole/{userId}")
    public ResponseEntity<?> assignRole(@PathVariable String userId, @RequestParam String role){
        keycloakService.assignRole(userId, role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteRole/{userId}")
    public ResponseEntity<?> deleteRole(@PathVariable String userId, @RequestParam String role){
        keycloakService.deleteRole(userId, role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getRole/{userId}")
    public ResponseEntity<List<RoleRepresentation>> getRole(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(keycloakService.getRoles(userId));
    }
}
