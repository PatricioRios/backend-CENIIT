package ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PutRolesOnUserRequest {
    private String uuid;

    private Set<String> roles;
}
