package com.core_user_service.service;

import com.core_user_service.configuration.keycloak.KeycloakManager;
import com.core_user_service.exception.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class KeyCloakUserService {

    private final KeycloakManager keycloakManager;

    public Integer createUser(UserRepresentation userRepresentation) {

        Response response = keycloakManager.getKeyCloakInstanceWithRealm().users().create(userRepresentation);
        return response.getStatus();
    }

    public void updateUser(UserRepresentation userRepresentation) {
        keycloakManager.getKeyCloakInstanceWithRealm().users().get(userRepresentation.getId()).update(userRepresentation);

    }

    public List<UserRepresentation> readUserByEmail(String mail) {
        return keycloakManager.getKeyCloakInstanceWithRealm().users().search(mail);
    }

    public UserRepresentation readUserBy(String authId) {
        try {
            UserResource userResource = keycloakManager.getKeyCloakInstanceWithRealm().users().get(authId);
            return userResource.toRepresentation();
        } catch (Exception e) {
            log.error("User Not Found With Given Id {}", e.toString());
            throw new EntityNotFoundException("User Not Found Under Given Id");
        }
    }


}
