package ar.edu.ceniit.demo.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello-admin")
    @PreAuthorize("hasRole('admin_backend')")
    public String helloAdmin() {
        return "Hello, World! admin (keycloak)";
    }

    @GetMapping("/hello-user")
    @PreAuthorize("hasRole('user_backend') or hasRole('admin_backend')")
    public String helloUser() {
        return "Hello, World! user (keycloak)";
    }
    @GetMapping("/hello-public")
    public String helloPublic() {
        return "Hello, World! public (keycloak)";
    }
}
