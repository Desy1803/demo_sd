package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.UserRequest;
import com.example.demo_sd.dto.UserResponse;
import com.example.demo_sd.dto.UserUpdateRequest;
import com.example.demo_sd.requests.EmailVerification;
import com.example.demo_sd.responses.KeycloakResponse;
import com.example.demo_sd.services.KeycloakService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/api/users/security")
@CrossOrigin()
public class KeycloakController {

    private final KeycloakService keycloakService;

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
        System.out.println("Refreshing token ");
        return keycloakService.refreshToken(refreshToken);
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




    @GetMapping("/get-user")
    @PreAuthorize("isAuthenticated()")
    public UserResponse getUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            if (principal != null) {
                System.out.println("Principal class: " + principal.getClass().getName());
                if (principal instanceof Jwt) {
                    Jwt jwt = (Jwt) principal;
                    String userId = jwt.getClaimAsString("sub");
                    System.out.println("user id: " + userId);
                    return new UserResponse(keycloakService.getUserInfo(userId));
                } else {
                    System.out.println("Principal is not an instance of Jwt");
                    throw new NotFoundException("Not found");
                }
            } else {
                System.out.println("Principal is null");
                throw new NotFoundException("Not found");
            }

        }else {
            throw new NotFoundException("Not found");
        }
    }


    @PutMapping("/update-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> updateUser(@RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName,
                                     @RequestParam(required = false) String username) {
        UserUpdateRequest userDetails = new UserUpdateRequest(username, firstName, lastName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            System.out.println("Principal class: " + principal.getClass().getName());
            if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                String userId = jwt.getClaimAsString("sub");
                if (userId == null) {
                    throw new IllegalArgumentException("Claim 'sub' not found in JWT");
                }

                keycloakService.updateUserInfo(userId, userDetails);
                return ResponseEntity.ok(Boolean.parseBoolean("true"));
            } else {
                throw new IllegalArgumentException("Principal is not an instance of Jwt");
            }
        }else {
            throw new NotFoundException("Not found");
        }

    }
    @PostMapping("/log-out")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity logOutUser( ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();

            System.out.println("Principal class: " + principal.getClass().getName());
            if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                String userId = jwt.getClaimAsString("sub");
                keycloakService.logoutUser(userId);
                return ResponseEntity.ok().build();
            } else {
                throw new NotFoundException("Not found");
            }
        }else {
            throw new NotFoundException("Not found");
        }

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody EmailVerification email){
        keycloakService.forgotPassword(email.getEmail());
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @DeleteMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() != null) {
                Object principal = authentication.getPrincipal();

                System.out.println("Principal class: " + principal.getClass().getName());
                if (principal instanceof Jwt) {
                    Jwt jwt = (Jwt) principal;
                    String userId = jwt.getClaimAsString("sub");
                    keycloakService.deleteUser(userId);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } else {
                    throw new NotFoundException("Not found");
                }
            }else {
                throw new NotFoundException("Not found");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
