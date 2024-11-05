package com.example.demo_sd.services;

import com.example.demo_sd.dto.UserRequest;
import com.example.demo_sd.responses.KeycloakResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

@Service
public class KeycloakService {


    private final String clientSecret = "ipGpTxezPErLjRTo8hoSBFkETX74coPh"; // Sostituisci con il tuo client secret

    private final Keycloak keycloak;
    private String realm;

    public KeycloakService( Keycloak keycloak) {
        this.keycloak = keycloak;
        realm="spring";
    }



    public KeycloakResponse loginUser(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "spring-app");
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response;
        try {
            String keycloakUrl = "http://localhost:8080/realms/spring/protocol/openid-connect/token";
            response = restTemplate.postForEntity(keycloakUrl, requestEntity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            KeycloakResponse responseKeycloak = objectMapper.readValue(response.getBody(), KeycloakResponse.class);

            return responseKeycloak;
        } catch (HttpClientErrorException e) {
            System.out.println("Errore durante il login: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw e;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public ResponseEntity<String> refreshToken(String refreshToken, String realm) {
        RestTemplate restTemplate = new RestTemplate();

        String body = String.format("grant_type=refresh_token&client_id=%s&client_secret=%s&refresh_token=%s",
                "spring-app", clientSecret, refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange("http://localhost:8080/auth", HttpMethod.POST, entity, String.class, realm);
    }


    public void createUser(UserRequest userRequest) {
        try{
            Map<String, List<String>> attributes = new HashMap<>();
            attributes.put("address", List.of(userRequest.getAddress()));
            attributes.put("country", List.of(userRequest.getCountry()));
            attributes.put("city", List.of(userRequest.getCity()));
            attributes.put("address", List.of(userRequest.getAddress()));
            attributes.put("phoneNumber", List.of(userRequest.getPhoneNumber()));
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setAttributes(attributes);
            user.setEnabled(true);
            user.setEmailVerified(false);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(userRequest.getPassword());
            credential.setTemporary(false);
            List<CredentialRepresentation> credentials = new ArrayList<CredentialRepresentation>();
            credentials.add(credential);
            user.setCredentials(credentials);
            UsersResource userResource = getUserResource();
            Response response = userResource.create(user);
            if (Objects.equals(201, response.getStatus())) {
                System.out.println("User created in Keycloak");
            List<UserRepresentation> userRepresentationsList = userResource.searchByUsername(userRequest.getUsername(), true);
            UserRepresentation userRepresentation= userRepresentationsList.get(0);
            sendVerificationEmail(userRepresentation.getId());
            } else {
                String errorMessage = response.readEntity(String.class);
                System.err.println("User not created. Response: " + response.getStatus() + " - " + errorMessage);
                throw new RuntimeException("User not created. Error: " + errorMessage);
            }

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public void forgotPassword(String username){
        UsersResource usersResource = getUserResource();
        List<UserRepresentation> userRepresentationsList = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation= userRepresentationsList.get(0);
        UserResource userResource= usersResource.get(userRepresentation.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public void deleteUser(String userId){
        UsersResource usersResource = getUserResource();
        usersResource.delete(userId);
    }


    public void sendVerificationEmail(String userId){
        UsersResource usersResource = getUserResource();
        usersResource.get(userId).sendVerifyEmail();
    }
    private UsersResource getUserResource(){
        return keycloak.realm(realm).users();
    }

    private UserResource getUser(String userId){
        UsersResource usersResource = getUserResource();
        return usersResource.get(userId);
    }


    public String getUserIdByUsername(String username) {
        try {
            UsersResource usersResource = getUserResource();
            List<UserRepresentation> users = usersResource.searchByUsername(username, true);

            if (users.isEmpty()) {
                throw new RuntimeException("User not found: " + username);
            }
            return users.get(0).getId();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user ID: " + e.getMessage(), e);
        }
    }

}
