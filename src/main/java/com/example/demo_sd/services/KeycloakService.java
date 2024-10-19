package com.example.demo_sd.services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private String realm;

    public KeycloakService( Keycloak keycloak) {
        this.keycloak = keycloak;
        realm="spring";
    }


    public void createUser(String username, String email, String firstName, String lastName, String password) {
        try{
            UserRepresentation user = new UserRepresentation();
            user.setUsername(username);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEnabled(true);
            user.setEmailVerified(false);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);
            List<CredentialRepresentation> credentials = new ArrayList<CredentialRepresentation>();
            credentials.add(credential);
            user.setCredentials(credentials);
            UsersResource userResource = getUserResource();
            Response response = userResource.create(user);
            if (Objects.equals(201, response.getStatus())) {
                System.out.println("User created in Keycloak");
            List<UserRepresentation> userRepresentationsList = userResource.searchByUsername(username, true);
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

    public void assignRole(String userId, String roleName){
        UserResource userResource = getUser(userId);
        RolesResource rolesResource= getRolesResource();
        RoleRepresentation roleRepresentation= rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public void deleteRole(String userId, String roleName){
        UserResource userResource = getUser(userId);
        RolesResource rolesResource= getRolesResource();
        RoleRepresentation roleRepresentation= rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().remove(Collections.singletonList(roleRepresentation));
    }
    public void sendVerificationEmail(String userId){
        UsersResource usersResource = getUserResource();
        usersResource.get(userId).sendVerifyEmail();
    }
    public List<RoleRepresentation> getRoles(String userId){
        return getUser(userId).roles().realmLevel().listAll();
    }
    private UsersResource getUserResource(){
        return keycloak.realm(realm).users();
    }

    private UserResource getUser(String userId){
        UsersResource usersResource = getUserResource();
        return usersResource.get(userId);
    }

    private RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }
}
