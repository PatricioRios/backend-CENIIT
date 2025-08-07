package ar.edu.ceniit.demo.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class KeycloakProvider {

    @Value("${keycloak.provider.config.server.url}")
    private static String SERVER_URL ;//localhost:9090";
    @Value("${keycloak.provider.config.realm.namel}")
    private static String REALM_NAME ;
    @Value("${keycloak.provider.config.realm.master}")
    private static String REALM_MASTER ;
    @Value("${keycloak.provider.config.admin.cli}")
    private static String ADMIN_CLI ;
    @Value("${keycloak.provider.config.user.console}")
    private static String USER_CONSOLE ;
    @Value("${keycloak.provider.config.password.console}")
    private static String PASSWORD_CONSOLE ;
    @Value("${keycloak.provider.config.client.secret}")
    private static String CLIENT_SECRET ;

    public static RealmResource getRealmResource() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER)
                .clientId(ADMIN_CLI)
                .username(USER_CONSOLE)
                .password(PASSWORD_CONSOLE)
                .clientSecret(CLIENT_SECRET)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();

        return keycloak.realm(REALM_NAME);
    }

    public static UsersResource getUserResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
