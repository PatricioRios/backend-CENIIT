package ar.edu.ceniit.demo.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.provider.config.server.url}")
    private String serverUrl;
    @Value("${keycloak.provider.config.realm.name}")
    private String realmName;
    @Value("${keycloak.provider.config.realm.master}")
    private String realmMaster;
    @Value("${keycloak.provider.config.admin.cli}")
    private String adminCli;
    @Value("${keycloak.provider.config.user.console}")
    private String userConsole;
    @Value("${keycloak.provider.config.password.console}")
    private String passwordConsole;
    @Value("${keycloak.provider.config.client.secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmMaster)
                .clientId(adminCli)
                .username(userConsole)
                .password(passwordConsole)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloakAdminClient) {
        return keycloakAdminClient.realm(realmName);
    }

    @Bean
    public UsersResource usersResource(RealmResource realmResource) {
        return realmResource.users();
    }


}
