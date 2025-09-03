package ar.edu.ceniit.demo.auth.aplication.ports.input.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
