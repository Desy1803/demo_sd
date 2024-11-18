package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.UserRequest;
import com.example.demo_sd.entities.UserEntity;
import com.example.demo_sd.repositories.UserRepository;
import com.example.demo_sd.requests.EmailVerification;
import com.example.demo_sd.responses.KeycloakResponse;
import com.example.demo_sd.services.KeycloakService;
import org.jboss.resteasy.annotations.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<Boolean> registerUser(@RequestBody UserRequest user) {
        try {
            keycloakService.createUser(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestParam String refreshToken) {
        return keycloakService.refreshToken(refreshToken, "spring");
    }

    @PostMapping("/send-verification-email")
    public ResponseEntity<Boolean> sendVerificationEmail(@RequestBody EmailVerification email){
        try {
            keycloakService.sendVerificationEmail(email.getEmail());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verified-email")
    public ResponseEntity<Boolean> isVerifiedEmail(@RequestBody EmailVerification email){
        try {
            boolean check = keycloakService.verificationIsValid(email.getEmail());
            if(check)
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public KeycloakResponse login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        try {
            System.out.println(username + " " + password);
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

    @PutMapping("/forgot-password")
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
